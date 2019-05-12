package org.grocery.Subscription;

import org.grocery.Error.GroceryException;
import org.grocery.item.Item;
import org.grocery.item.ItemDataWithQuantity;
import org.grocery.item.ItemQuantity;
import org.grocery.item.ItemService;
import org.joda.time.DateTime;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SubscriptionService {

    @Autowired
    ItemService itemService;

    @Autowired
    SubscriptionDao subscriptionDao;

    @Autowired
    OptionalMappingHelper<Subscription, GroceryException> optionalMappingHelper;

    public Double addSubscription(SubscriptionInputData data) throws GroceryException {
        Timestamp current = currentTimestamp();
        Subscription subscription = new Subscription();
        subscription.setUserId(data.getUserId());
        subscription.setStartDate(data.getStartDate());
        subscription.setTotalSubscriptionOrders(data.getNumOfSubscriptions());
        subscription.setCreatedAt(currentTimestamp());
        subscription.setFrequency(convertArrayListToString(data.getFrequency()));
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setCreatedAt(current);
        subscription.setUpdatedAt(current);
        List<SubscriptionOrder> subscriptionOrders = createSubscriptionOrders(subscription, data.getItemsWithQuantity(),
                subscription.getStartDate(), current);
        subscription.setSubscriptionOrders(subscriptionOrders);
        Double cartPrice = itemService.getCartPrice(data.getItemsWithQuantity());
        subscriptionDao.update(subscription);
        return cartPrice * data.getNumOfSubscriptions();
    }

    public void pauseSubscription(long id) throws GroceryException {
        Timestamp current = currentTimestamp();
        Subscription subscription = optionalMappingHelper.getOrElse(subscriptionDao.findById(id), new GroceryException());
        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new GroceryException();
        } else {
            subscription.setStatus(SubscriptionStatus.PAUSED);
            subscription.setUpdatedAt(current);
            List<SubscriptionOrder> updatedSubscriptionOrders =
                    subscription.getSubscriptionOrders().stream().peek(subscriptionOrder -> {
                        if (subscriptionOrder.getOrderStatus() == SubscriptionOrderStatus.PENDING_FOR_DELIVERY) {
                            subscriptionOrder.setOrderStatus(SubscriptionOrderStatus.SUBSCRIPTION_PAUSED);
                            subscriptionOrder.setUpdatedAt(current);
                        }
                    }).collect(Collectors.toList());
            subscription.setSubscriptionOrders(updatedSubscriptionOrders);
            subscriptionDao.update(subscription);
        }
    }

    public Double cancelSubscription(long id) throws GroceryException {
        Timestamp current = currentTimestamp();
        Double refundCharges = 0D;
        Subscription subscription = optionalMappingHelper.getOrElse(subscriptionDao.findById(id), new GroceryException());
        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new GroceryException();
        } else {
            subscription.setStatus(SubscriptionStatus.CANCELLED);
            subscription.setUpdatedAt(current);
            List<SubscriptionOrder> updatedSubscriptionOrders = new ArrayList<SubscriptionOrder>();
            for (SubscriptionOrder order: subscription.getSubscriptionOrders()) {
                if (order.getOrderStatus() == SubscriptionOrderStatus.PENDING_FOR_DELIVERY &&
                        order.getDeliveryDate().after(current)) {
                    refundCharges += order.getPrice();
                    order.setOrderStatus(SubscriptionOrderStatus.SUBSCRIPTION_CANCELLED);
                    order.setUpdatedAt(current);
                }
                updatedSubscriptionOrders.add(order);
            }
            subscription.setSubscriptionOrders(updatedSubscriptionOrders);
            subscriptionDao.update(subscription);
            return refundCharges;
        }
    }

    public void resumeSubscription(long id, Timestamp resumeDate) throws GroceryException {
        Subscription subscription = optionalMappingHelper.getOrElse(subscriptionDao.findById(id), new GroceryException());
        Timestamp current = currentTimestamp();
        if (subscription.getStatus() != SubscriptionStatus.CANCELLED) {
            throw new GroceryException();
        } else {
            List<SubscriptionOrder> pendingOrders = subscription.getSubscriptionOrders()
                    .stream().filter(order -> order.getOrderStatus() == SubscriptionOrderStatus.SUBSCRIPTION_PAUSED)
                    .collect(Collectors.toList());
            List<SubscriptionOrder> completedOrders = subscription.getSubscriptionOrders().stream()
                    .filter(pendingOrders::contains).collect(Collectors.toList());
            List<Timestamp> newDeliveryDates = evaluateDeliveryDates(new DateTime(resumeDate),
                    getArrayListFromString(subscription.getFrequency()), pendingOrders.size());
            for (int i = 0; i < pendingOrders.size(); i++) {
                pendingOrders.get(i).setDeliveryDate(newDeliveryDates.get(i));
                pendingOrders.get(i).setUpdatedAt(current);
            }
            subscription.setStatus(SubscriptionStatus.ACTIVE);
            subscription.setSubscriptionOrders(Stream.concat(completedOrders.stream(), pendingOrders.stream())
                    .collect(Collectors.toList()));
            subscription.setUpdatedAt(current);
            subscriptionDao.update(subscription);
        }
    }

    public void markDeliveryByDate(long id, Timestamp date) throws GroceryException {
        Subscription subscription = optionalMappingHelper.getOrElse(subscriptionDao.findById(id), new GroceryException());
        Timestamp current = currentTimestamp();
        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new GroceryException();
        } else {
            List<SubscriptionOrder> updatedOrders = subscription.getSubscriptionOrders().stream().peek(subscriptionOrder -> {
                if (isSameDay(subscriptionOrder.getDeliveryDate(), date)) {
                    subscriptionOrder.setOrderStatus(SubscriptionOrderStatus.DELIVERED);
                    subscriptionOrder.setUpdatedAt(current);
                }
            }).collect(Collectors.toList());
            subscription.setSubscriptionOrders(updatedOrders);
            if (updatedOrders.stream().noneMatch(order -> order.getOrderStatus() != SubscriptionOrderStatus.DELIVERED))
                subscription.setStatus(SubscriptionStatus.COMPLETED);
            subscription.setUpdatedAt(current);
            subscriptionDao.update(subscription);
        }
    }

    public List<SubscriptionData> subscriptionsByUser(long userId) {
        return subscriptionDao.findByUser(userId).stream().map(subscription -> {
            SubscriptionData outputData = new SubscriptionData();
            outputData.setFrequency(getArrayListFromString(subscription.getFrequency()));
            long subscriptionsLeft = subscription.getSubscriptionOrders().stream().filter(o ->
                    o.getOrderStatus() == SubscriptionOrderStatus.PENDING_FOR_DELIVERY).count();
            outputData.setNumOfSubscriptionsLeft(subscriptionsLeft);
            outputData.setUserId(userId);
            outputData.setSubscriptionId(subscription.getId());
            return outputData;
        }).collect(Collectors.toList());
    }

    private Boolean isSameDay(Timestamp timestamp1, Timestamp timestamp2) {
        DateTime dateTime1 = new DateTime(timestamp1).withTimeAtStartOfDay();
        DateTime dateTime2 = new DateTime(timestamp2).withTimeAtStartOfDay();
        return dateTime1.isEqual(dateTime2);
    }

    private List<SubscriptionOrder> createSubscriptionOrders(Subscription subscription,
                                                             List<ItemQuantity> cart,
                                                             Timestamp startDate,
                                                             Timestamp current) throws GroceryException {
        List<SubscriptionOrder> subscriptionOrders = new ArrayList<SubscriptionOrder>();
        List<Timestamp> deliveryDates = evaluateDeliveryDates(new DateTime(startDate),
                getArrayListFromString(subscription.getFrequency()), subscription.getTotalSubscriptionOrders());
        Double pricePerCart = itemService.getCartPrice(cart);
        for (Timestamp t: deliveryDates) {
            SubscriptionOrder order = new SubscriptionOrder();
            order.setDeliveryDate(t);
            order.setUpdatedAt(current);
            order.setPrice(pricePerCart);
            order.setOrderStatus(SubscriptionOrderStatus.PENDING_FOR_DELIVERY);
            List<SubscriptionOrderItem> orderItems = createOrderItems(cart, order);
            order.setSubscriptionOrderItems(orderItems);
            order.setSubscription(subscription);
            subscriptionOrders.add(order);
        }
        return subscriptionOrders;
    }

    private List<SubscriptionOrderItem> createOrderItems(List<ItemQuantity> cart, SubscriptionOrder order) {
        return cart.stream().map(itemQuantity -> {
            SubscriptionOrderItem orderItem = new SubscriptionOrderItem();
            orderItem.setItemId(itemQuantity.getItemId());
            orderItem.setQuantity(itemQuantity.getQuantity());
            orderItem.setSubscriptionOrder(order);
            return orderItem;
        }).collect(Collectors.toList());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private String convertArrayListToString(ArrayList<Integer> arrList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrList.size(); i++) {
            int num = arrList.get(i);
            sb.append(num);
        }
        return sb.toString();
    }

    private ArrayList<Integer> getArrayListFromString(String s) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < s.length(); i++) {
            int num = Character.getNumericValue(s.charAt(i));
            result.add(num);
        }
        return result;
    }

    private List<Timestamp> evaluateDeliveryDates(DateTime startDate,
                                                 ArrayList<Integer> frequency,
                                                 Integer subscriptionsLeft) {
        List<Timestamp> deliveryDates = new ArrayList<Timestamp>();
        int dayOfWeek = startDate.dayOfWeek().get() - 1;
        DateTime current = startDate;
        do {
            if (frequency.get(dayOfWeek) != 1) {
                dayOfWeek = (dayOfWeek+1)%7;
            } else {
                deliveryDates.add(new Timestamp(current.withTimeAtStartOfDay().getMillis()));
                dayOfWeek = (dayOfWeek+1)%7;
                subscriptionsLeft--;
            }
            current = current.plusDays(1);
        } while(subscriptionsLeft > 0);
        return deliveryDates;
    }

}
