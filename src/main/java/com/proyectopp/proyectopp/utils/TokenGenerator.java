package com.proyectopp.proyectopp.utils;

import java.util.UUID;

public class TokenGenerator {
    public static String generateShortToken() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }
}

