package com.example.demo.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DomainTransactional {

    enum Propagation {
        REQUIRED(0),
        SUPPORTS(1),
        MANDATORY(2),
        REQUIRES_NEW(3),
        NOT_SUPPORTED(4),
        NEVER(5),
        NESTED(6);

        private final int value;

        Propagation(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    Propagation propagation() default Propagation.REQUIRED;

    int timeout() default -1;
}
