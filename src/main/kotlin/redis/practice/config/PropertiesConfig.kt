package redis.practice.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import redis.practice.distributelock.properties.RedisProperties

@Configuration
@EnableConfigurationProperties(
    value = [
        RedisProperties::class,
    ],
)
class PropertiesConfig
