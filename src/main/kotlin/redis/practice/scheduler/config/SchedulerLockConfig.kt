package redis.practice.scheduler.config

import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory


@Configuration
/**
 * 기본적으로 잠기는 시간
 * (실행 노드가 죽었을 때도 잠금 해제를 할 수 있게 하도록)
 **/
@EnableSchedulerLock(defaultLockAtMostFor = "2m")
class SchedulerLockConfig {
    @Bean
    fun lockProvider(connectionFactory: RedisConnectionFactory): LockProvider {
        return RedisLockProvider(connectionFactory)
    }
}