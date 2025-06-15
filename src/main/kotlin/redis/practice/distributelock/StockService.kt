package redis.practice.distributelock


import org.springframework.stereotype.Service
import redis.practice.distributelock.annotation.RedissonLock

@Service
class StockService {
    private val stockMap = mutableMapOf<Long, Stock>()

    @RedissonLock(lockName = "stock:#{#productId}")
    fun decreaseStock(productId: Long, quantity: Int) {
        val stock = findStock(productId)

        if (stock.quantity < quantity) {
            throw IllegalStateException("재고가 부족합니다.")
        }

        stock.decrease(quantity)
    }

    fun findStock(productId: Long): Stock {
        return stockMap.getOrPut(productId) { Stock(productId) }
    }
}

data class Stock(
    val productId: Long,
    var quantity: Int = 100
) {
    fun decrease(quantity: Int) {
        this.quantity -= quantity
    }
}