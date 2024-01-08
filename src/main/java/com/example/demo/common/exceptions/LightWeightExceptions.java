package com.example.demo.common.exceptions;

public class LightWeightExceptions extends RuntimeException {

    public LightWeightExceptions(String message) {
        super(message, null, false, false);
    }
}
