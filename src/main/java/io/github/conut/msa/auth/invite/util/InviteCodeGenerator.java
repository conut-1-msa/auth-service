package io.github.conut.msa.auth.invite.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class InviteCodeGenerator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6;

    public String generate() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int idx = SECURE_RANDOM.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(idx));
        }
        return sb.toString();
    }
}
