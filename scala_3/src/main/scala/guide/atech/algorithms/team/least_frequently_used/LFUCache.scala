package guide.atech.algorithms.team.least_frequently_used

import scala.collection.mutable

case class Item[K, V]( key: K, value: V, frequency: Int)

class LFUCache[K, V](limit: Int) extends Cache[K, V] {

  private val cache = mutable.Map.empty[K, Item[K, V]]

  private val ordering = Ordering.fromLessThan[Item[K, V]]((a, b) => b.frequency < a.frequency)
  private val minHeap = mutable.PriorityQueue.empty[Item[K, V]](ordering)

  override def put(key: K, value: V): Option[V] = {

    if (cache.contains(key)) {

      val item = cache(key)
      val newItem = item.copy(frequency = item.frequency + 1)

      cache.put(key, newItem)
      minHeap.enqueue(newItem)
      Some(item.value)
    }
    else {
      if (cache.size == limit) {
        val evictKey = getEvictionKey
        cache.remove(evictKey)
      }

      val item = Item(key, value, 1)

      cache.put(key, item)
      minHeap.enqueue(item)
      None
    }

  }

  override def get(key: K): Option[V] = {
    cache.get(key) match
      case Some(item) =>

        val newItem = item.copy(frequency = item.frequency + 1)

        cache.put(key, newItem)
        minHeap.enqueue(newItem)
        Some(item.value)
      case None => None
  }

  private def getEvictionKey: K = {
    var item = minHeap.dequeue()

    // remove invalid items
    while (item.frequency != cache(item.key).frequency) {
      item = minHeap.dequeue()
    }

    item.key
  }
}
