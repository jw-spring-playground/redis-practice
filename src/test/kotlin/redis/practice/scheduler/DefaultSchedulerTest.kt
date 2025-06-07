package redis.practice.scheduler

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DefaultSchedulerTest {
    @Autowired
    lateinit var defaultScheduler: DefaultScheduler

    @Test
    fun `스케줄러는 lock이 걸려있을 때는 실행되지 않는다`() {
        Thread.sleep(5000 * 2)

        assertEquals(1, defaultScheduler.counter.get())
    }
}
