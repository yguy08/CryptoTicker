package com.tickercash.tapereader.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;

import com.google.inject.BindingAnnotation;

/**
 * Annotates the Path to Esper Module
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@BindingAnnotation
public @interface EsperStatement {

}
