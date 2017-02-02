package com.sarwarecommerce.sarwarapp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by mkodekar on 02/02/2017.
 */

@Retention(RUNTIME) @Target(TYPE) public @interface Layout {
    int value();
}
