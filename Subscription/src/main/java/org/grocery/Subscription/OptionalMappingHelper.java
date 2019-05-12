package org.grocery.Subscription;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionalMappingHelper<T, R extends Throwable> {
    public T getOrElse(Optional<T> optionalParam, R ifNotPresentThrowable) throws R {
        if (optionalParam.isPresent())
            return optionalParam.get();
        else
            throw ifNotPresentThrowable;
    }
}
