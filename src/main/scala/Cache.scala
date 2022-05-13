import zio._
import java.time._

/*

 * Max size of cache (10,000)
 *
 * Eviction criteria:
 *  - fixed date expiration
 *  - time since creation?
 *  - time since last access (how often is it used?)
 *  - time since last refresh (how old is it?)
 *  - count of usage? (how often has it been used?)
 *  - references to the entry? (any references left?)
 *  - Size of entry? (How much memory does it consume?)
 *  - Cache-wide Statistics
 *  - Value based criteria?
 *
 * Ability to "write" loaded values, e.g. store on disk after retrieving remote values
 * Notification of eviction
 * LRU/Adaptive



 */

final case class ValueStats(accumulatedSize: Long)

final case class EntryStats(created: Instant,
                            accessed: Instant,
                            loaded: Instant,
                            hits: Long,
                            misses: Long,
                            lookups: Long,
                            evictions: Long,
                            curSize: Long,
                            accSize: Long,
                            valueStats: ValueStats
) {
  def total: Long = hits + misses
  def size: Long = accSize / lookups
}

final case class CacheStats(entryCount: Int,
                            memorySize: Long,
                            hits: Long,
                            misses: Long,
                            lookups: Long,
                            evictions: Long,
                            loadPenalty: Duration
)

object Exploration1 {
  // Cons: Cannot implement LRU!
  final case class CacheExpirationPolicy[-Value](
    expire: (Instant, CacheStats, EntryStats) => Boolean
  ) {
    def &&[Value1 <: Value](that: CacheExpirationPolicy[Value1]): CacheExpirationPolicy[Value1] = ???
    def ||[Value1 <: Value](that: CacheExpirationPolicy[Value1]): CacheExpirationPolicy[Value1] = ???

  }
  object CacheExpirationPolicy {
    def olderThan(duration: Duration): CacheExpirationPolicy[Any] = ???
  }
}

object Exploration2 {
  type Entry[+Value] = (Instant, CacheStats, EntryStats, Value)
  final case class CacheExpirationPolicy[-Value](
    lessThan: (Entry[Value], Entry[Value]) => Boolean
  )
}

final case class Lookup[-Key, -R, +E, +Value](value: Key => ZIO[R, E, Value]) extends Key => ZIO[R, E, Value] {
  def apply(v1: Key): ZIO[R, E, Value] = value(v1)
}
trait Cache[-Key, +E, +Value] {
  def get(k: Key): IO[E, Value]
}

object Cache {
  def make[Key, R, E, Value](lookup: Key => ZIO[R, E, Value]): ZIO[R, Nothing, Cache[Key, E, Value]] =
    ZIO.environment[R].map { env =>
      new Cache[Key, E, Value] {
        def get(key: Key): IO[E, Value] = lookup(key).provide(env)
      }
    }
}
