package com.tapereader.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;

import com.google.inject.BindingAnnotation;

/**
 * Annotates the Broker URL of Active MQ Broker
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@BindingAnnotation
public @interface ActiveMQBrokerURL {

}
