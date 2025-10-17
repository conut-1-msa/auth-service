package io.github.conut.msa.auth.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;

public class JwtUtilTests {
    private static final String SECRET = "jsonwebtokensecretkeyfortestcode";
    private static final String TEST_UUID = "test-uuid";
    private JwtUtil jwtUtil = new JwtUtil(SECRET);

    @Test
    void generateAndParse_ok() {
        Map<String, Object> claims = Map.of("uuid", TEST_UUID);
        Date exp = Date.from(Instant.now().plusSeconds(60));

        String token = jwtUtil.generateToken(claims, exp);
        Claims parsed = jwtUtil.parseToken(token);

        assertEquals(TEST_UUID, parsed.get("uuid", String.class));
        assertTrue(Math.abs(exp.getTime() - parsed.getExpiration().getTime()) < 1000);
    }

    @Test
    void parseExpiredToken_throwsExpiredJwtException() {
        Map<String, Object> claims = Map.of("uuid", TEST_UUID);
        Date exp = Date.from(Instant.now().minusSeconds(5));

        String token = jwtUtil.generateToken(claims, exp);

        assertThrows(ExpiredJwtException.class, () -> jwtUtil.parseToken(token));
    }

    @Test
    void signWithDifferentSecret_throwsSignatureException() {
        Map<String, Object> claims = Map.of("uuid", TEST_UUID);
        Date exp = Date.from(Instant.now().plusSeconds(60));

        JwtUtil differentJwtUtil = new JwtUtil("differentjsonwebtokensecretkeyfortestcode");
        String token = differentJwtUtil.generateToken(claims, exp);

        assertThrows(SignatureException.class, () -> jwtUtil.parseToken(token));
    }

    @Test
    void constructWithShortSecret_throwsWeakKeyException() {
        String shortKey = "shortkey";

        assertThrows(WeakKeyException.class, () -> new JwtUtil(shortKey));
    }

    @Test
    void parseMalformedToken_throwsMalformedJwtException() {
        String malformedToken = "malformedToken";

        assertThrows(MalformedJwtException.class, () -> jwtUtil.parseToken(malformedToken));
    }
}
