// src/main/java/com/kifiya/exception/CafeNotFoundException.java
package com.kifiya.exception;

public class CafeNotFoundException extends RuntimeException {
    public CafeNotFoundException(String message) {
        super(message);
    }
}

