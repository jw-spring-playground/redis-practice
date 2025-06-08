package redis.practice.distributelock.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.practice.distributelock.properties.RedisProperties

@Configuration
class DistributeLockConfig(
    private val redisProperties: RedisProperties,
) {
    /**
     * redisAddress는 redis://host:port로 구성
     * useSingleServer() : 단일 서버일 때
     * useClusterServers(): 클러스터가 여러 개(여러 노드, 샤딩 지원) 일 떄 사용
     * useSentinelServers(): redis 환경이 sentinel일 때 (마스터 자동 감지, failover 지원 등)
     * useMasterSlaveServers()
     **/
    @Bean
    fun redissonClient(): RedissonClient {
        val redisAddress = "${REDIS_ADDRESS_PREFIX}${redisProperties.redisHost}:${redisProperties.redisPort}"
        val config = Config()
        config.useSingleServer().setAddress(redisAddress)

//        클러스터
//        config.useClusterServers()
//            .addNodeAddress("redis://host1:6379", "redis://host2:6379", "redis://host3:6379")

//        마스터-슬레이브
//        config.useMasterSlaveServers()
//            .setMasterAddress("redis://master-host:6379")
//            .addSlaveAddress("redis://slave1-host:6379", "redis://slave2-host:6379")

//        센티넬
//        config.useSentinelServers()
//            .setMasterName("master")
//            .addSentinelAddress("redis://host1:26379", "redis://host2:26379")

        return Redisson.create(config)
    }

    companion object {
        const val REDIS_ADDRESS_PREFIX = "redis://"
    }
}
