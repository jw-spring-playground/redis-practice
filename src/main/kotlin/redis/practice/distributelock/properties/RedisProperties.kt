package redis.practice.distributelock.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data")
data class RedisProperties(
    val redisPort: Int,
    val redisHost: String,
)
