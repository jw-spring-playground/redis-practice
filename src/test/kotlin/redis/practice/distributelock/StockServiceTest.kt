package redis.practice.distributelock

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class StockServiceTest {

    @Autowired
    private lateinit var stockService: StockService

    private val productId = 1L
    private val initQuantity = 100

    private val initStock = Stock(productId, initQuantity)

    @Test
    fun `재고보다 많은 수량을 요청하면 예외가 발생한다`() {
        // given
        val quantity = initQuantity + 1

        // then
        assertThatThrownBy { stockService.decreaseStock(productId, quantity) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("재고가 부족합니다.")
    }

    @Test
    fun `동시에 100개의 요청이 들어와도 재고가 정확하게 감소한다`() {
        // given
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount * 2)

        // when
        repeat(threadCount) {
            executorService.submit {
                try {
                    stockService.decreaseStock(productId, 1)
                    stockService.decreaseStock(productId, 1)
                } finally {
                    latch.countDown()
                    latch.countDown()
                }
            }
        }
        latch.await()

        // then
        val stock = stockService.findStock(productId)
        assertThat(stock.quantity).isEqualTo(initStock.quantity - threadCount)
    }

    @Test
    fun `여러 상품의 재고를 동시에 감소시켜도 각각 정확하게 감소한다`() {
        // given
        val product1Id = 1L
        val product2Id = 2L
        val threadCount = 50
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        // when
        repeat(threadCount) {
            executorService.submit {
                try {
                    stockService.decreaseStock(product1Id, 1)
                    stockService.decreaseStock(product2Id, 1)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        // then
        val stock1 = stockService.findStock(product1Id)
        val stock2 = stockService.findStock(product2Id)
        assertThat(stock1.quantity).isEqualTo(initStock.quantity - threadCount)
        assertThat(stock2.quantity).isEqualTo(initStock.quantity - threadCount)
    }
}