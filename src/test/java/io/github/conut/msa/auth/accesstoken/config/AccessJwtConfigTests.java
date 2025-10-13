package io.github.conut.msa.auth.accesstoken.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.support.TestPropertySourceUtils;

import io.github.conut.msa.auth.common.util.JwtUtil;

@SpringBootTest(classes = AccessJwtConfig.class)
@ActiveProfiles("test")
public class AccessJwtConfigTests {
    @Autowired
    JwtUtil accessJwtUtil;

    @Test
    void createBean_ok() {
        assertNotNull(accessJwtUtil);
    }
}

class AccessJwtConfigWeakKeyTests {
    @Test
    void createBeanWithWeakKey_throwsBeanCreationException() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context, "jwt.access-token-secret=shortkey");
        context.register(AccessJwtConfig.class);

        assertThrows(BeanCreationException.class, context::refresh);

        context.close();
    }
}
