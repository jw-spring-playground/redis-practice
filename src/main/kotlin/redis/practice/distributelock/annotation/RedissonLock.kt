package redis.practice.distributelock.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedissonLock(
    val lockName: String,
    val waitTime: Long = DEFAULT_WAIT_TIME,
    val leaseTime: Long = DEFAULT_LEASE_TIME,
) {
    companion object {
        const val DEFAULT_WAIT_TIME: Long = 5
        const val DEFAULT_LEASE_TIME: Long = 10
    }
}
