package redis.practice.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DefaultScheduler {
    /**
     * 스케줄러와 lock은 별개로 동작
     * 스케줄러 주기가 더 빠르다면 lock에 걸려 작동하지 않는다.
     **/
    @Scheduled(fixedDelayString = "5000")
    @SchedulerLock(
        lockAtMostFor = "1m",
        lockAtLeastFor = "1m",
        name = "EXAMPLE",
    )
    fun example() {
        kLogger.info { "scheduler start" }
        Thread.sleep(5000)
        kLogger.info { "scheduler end" }
    }

    companion object {
        val kLogger = KotlinLogging.logger {}
    }
}
