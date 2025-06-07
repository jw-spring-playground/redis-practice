package redis.practice.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DefaultScheduler {
    @Scheduled(fixedDelayString = "60000")
    @SchedulerLock(
        lockAtMostFor = "1m",
        lockAtLeastFor = "1m",
        name = "EXAMPLE"
    )
    fun example() {
        kLogger.info { "scheduler start" }
        Thread.sleep(50000)
        kLogger.info { "scheduler end" }
    }

    companion object {
        val kLogger = KotlinLogging.logger {}
    }
}