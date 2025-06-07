package redis.practice.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class DefaultScheduler {
    val counter = AtomicInteger(0)

    /**
     * 스케줄러와 lock은 별개로 동작
     * 스케줄러 주기가 더 빠르다면 lock에 걸려 작동하지 않는다.
     * lockAtLeastFor를 설정하지 않았다면 task가 끝나면 lock 해제
     **/
    @Scheduled(fixedDelayString = "\${scheduler.example.delay-millisecond}")
    @SchedulerLock(
        lockAtMostFor = "\${scheduler.example.lock-at-most-for}",
        lockAtLeastFor = "\${scheduler.example.lock-at-least-for}",
        name = "\${scheduler.example.lock-name}",
    )
    fun example() {
        counter.incrementAndGet()
        kLogger.info { "scheduler start" }
        Thread.sleep(5000)
        kLogger.info { "scheduler end" }
    }

    companion object {
        val kLogger = KotlinLogging.logger {}
    }
}
