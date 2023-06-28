package guide.atech.algorithms.team.fs

class FileSystem {
  import scala.collection.mutable.Map
  import java.util.PriorityQueue

  var totalSize = 0
  private val collectionSizeMap = Map.empty[Collection, Int]

  private val ordering = Ordering.fromLessThan[(Collection, Int)]((a, b) => a._2 > b._2)
  private val maxHeap = PriorityQueue[(Collection, Int)](ordering)

  // O(N)
  def addFile(file: MyFile): Unit = {
    totalSize += file.size

    file.collection match {
      case Some(collection) =>
        val currentCollectionSize = collectionSizeMap.getOrElse(collection, 0)
        val newCollectionSize = currentCollectionSize + file.size
        collectionSizeMap.put(collection, newCollectionSize)

        if (currentCollectionSize > 0) {
          maxHeap.remove((collection, currentCollectionSize))
        }

        maxHeap.add((collection, newCollectionSize))
      case None =>
    }
  }

  // O(N)
  def getTopKCollection(k: Int): Array[Collection] = {
    this.maxHeap.toArray(Array[(Collection, Int)]()).take(k).map(_._1)
  }

}
