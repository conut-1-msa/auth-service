package io.github.conut.msa.auth.common.security.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
    public static final String PASSWORD_ENCODER_ID_BCRYPT = "bcrypt";

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = PASSWORD_ENCODER_ID_BCRYPT;
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put(PASSWORD_ENCODER_ID_BCRYPT, new BCryptPasswordEncoder());

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(new PlainPasswordEncoder());
        return passwordEncoder;
    }
}

class PlainPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(rawPassword.toString());
    }
}
