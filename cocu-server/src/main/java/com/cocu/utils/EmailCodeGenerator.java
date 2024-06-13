package com.cocu.utils;

import java.security.SecureRandom;
import java.util.Random;

public class EmailCodeGenerator {
    private static final String CHARACTERS = "1234567890";
    private static final int CODE_LENGTH = 6;
    private static final Random RANDOM = new SecureRandom();

    public static String generateVerificationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}