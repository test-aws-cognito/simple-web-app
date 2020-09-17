package com.gft.invesco.raisin.cognito.projectraisincognito;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.stereotype.Component;

@Component
public class RedisConfiguration {

    @Bean
    ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
