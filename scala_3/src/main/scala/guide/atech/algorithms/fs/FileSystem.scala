package guide.atech.algorithms.fs


class FileSystem {
  import scala.collection.mutable.Map
  import java.util.PriorityQueue

  var totalSize = 0
  private val collectionSizeMap = Map.empty[Collection, Int]

  private val ordering = Ordering.fromLessThan[(Collection, Int)]((a, b) => a._2 > b._2)
  private val maxHeap = PriorityQueue[(Collection, Int)](ordering)

  def addFile(file: MyFile): Unit = {
    totalSize += file.size

    file.collection match {
      case Some(collection) =>
        val collectionSize = collectionSizeMap.getOrElse(collection, 0)
        val newCollectionSize = collectionSize + file.size
        collectionSizeMap.put(collection, newCollectionSize)

        if (collectionSize > 0) {
          maxHeap.remove((collection, collectionSize))
          maxHeap.add((collection, newCollectionSize))
        } else {
          maxHeap.add((collection, newCollectionSize))
        }
      case None =>
    }
  }

  def getTopKCollection(k: Int): Array[Collection] = {
    this.maxHeap.toArray(Array[(Collection, Int)]()).take(k).map(_._1)
  }

}
