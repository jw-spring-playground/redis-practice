package redis.practice.distributelock

import org.springframework.stereotype.Service

@Service
class OrderService (
    private val stockService: StockService
) {

    fun createOrder(productId: Long, quantity: Int) {
        stockService.decreaseStock(productId, quantity)
    }
}