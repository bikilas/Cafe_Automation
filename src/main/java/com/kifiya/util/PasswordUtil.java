package com.kifiya.util;

public class PasswordUtil {
    public static String simpleHash(String password) {
        if (password == null) {
            return null;
        }
        return String.valueOf(password.hashCode());
    }
}
