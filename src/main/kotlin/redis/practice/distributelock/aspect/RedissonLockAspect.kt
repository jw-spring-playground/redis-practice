package redis.practice.distributelock.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import redis.practice.distributelock.annotation.RedissonLock
import java.util.concurrent.TimeUnit

@Aspect
@Component
class RedissonLockAspect(
    private val redissonClient: RedissonClient,
) {
    @Around("@annotation(redissonLock)")
    fun around(
        joinPoint: ProceedingJoinPoint,
        redissonLock: RedissonLock,
    ): Any? {
        val lock = redissonClient.getLock(redissonLock.lockName)
        val locked = lock.tryLock(redissonLock.waitTime, redissonLock.leaseTime, TimeUnit.SECONDS)
        if (!locked) throw IllegalStateException("Lock 획득 실패: ${redissonLock.lockName}")
        return try {
            joinPoint.proceed()
        } finally {
            if (lock.isHeldByCurrentThread) lock.unlock()
        }
    }
}
