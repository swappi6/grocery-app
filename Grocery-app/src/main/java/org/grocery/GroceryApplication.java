package org.grocery;

import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.Path;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.grocery.Auth.AuthFilter;
import org.grocery.Auth.AuthToken;
import org.grocery.Auth.AuthTokenDao;
import org.grocery.Offers.Offer;
import org.grocery.Offers.OfferDao;
import org.grocery.Error.GroceryExceptionMapper;
import org.grocery.User.User;
import org.grocery.User.UserDao;
import org.grocery.Utils.RedisService;
import org.grocery.admin.Admin;
import org.grocery.admin.AdminDao;
import org.grocery.category.Category;
import org.grocery.category.CategoryDao;
import org.grocery.config.GroceryConfiguration;
import org.grocery.config.GrocerySpringConfig;
import org.grocery.config.RedisConfiguration;
import org.grocery.item.Item;
import org.grocery.item.ItemDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class GroceryApplication extends Application<GroceryConfiguration> {

    public static void main(String[] args) throws Exception {
        new GroceryApplication().run(args);
    }
    

    private final HibernateBundle<GroceryConfiguration> hibernate = new HibernateBundle<GroceryConfiguration>(User.class , AuthToken.class, Category.class, Item.class, Admin.class ,Offer.class) {
        public DataSourceFactory getDataSourceFactory(GroceryConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
    
    private JedisPool initializeRedis(GroceryConfiguration configuration) {
        RedisConfiguration redisConfiguration = configuration.getRedisConfiguration();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        
        jedisPoolConfig.setMaxTotal(redisConfiguration.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisConfiguration.getMaxIdle());

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfiguration.getHostname(),
                redisConfiguration.getPort());

        return jedisPool;
    }

    @SuppressWarnings("resource")
    private void registerSpringConfig(GroceryConfiguration configuration, Environment env) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerSingleton("configuration", GroceryConfiguration.class);
        context.register(GrocerySpringConfig.class);
        context.refresh();
        Map<String, Object> resources = context.getBeansWithAnnotation(Path.class);
        for(Map.Entry<String,Object> entry : resources.entrySet()) {
            System.out.println(entry.getValue());
            env.jersey().register(entry.getValue());
        }
    }
    
    @Override
    public void initialize(Bootstrap<GroceryConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(GroceryConfiguration config, Environment env) {
        JedisPool jedisPool = initializeRedis(config);
        env.jersey().register(new GroceryExceptionMapper());
        env.jersey().register(AuthFilter.class);
        env.jersey().register(new RedisService(jedisPool));
        env.jersey().register(new UserDao(hibernate.getSessionFactory()));
        env.jersey().register(new AuthTokenDao(hibernate.getSessionFactory()));
        env.jersey().register(new CategoryDao(hibernate.getSessionFactory()));
        env.jersey().register(new ItemDao(hibernate.getSessionFactory()));
        env.jersey().register(new OfferDao(hibernate.getSessionFactory()));
        env.jersey().register(new AdminDao(hibernate.getSessionFactory()));
        registerSpringConfig(config, env);
        final FilterRegistration.Dynamic cors =
                env.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin,AUTH_TOKEN");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
    
    
}
