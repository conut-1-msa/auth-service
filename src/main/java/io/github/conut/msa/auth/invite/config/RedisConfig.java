package io.github.conut.msa.auth.invite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.github.conut.msa.auth.invite.vo.InviteCode;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, InviteCode> inviteCodeRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, InviteCode> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(InviteCode.class));

        return template;
    }
}
