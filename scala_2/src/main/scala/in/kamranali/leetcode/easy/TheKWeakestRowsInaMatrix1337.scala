package in.kamranali.leetcode.easy

object TheKWeakestRowsInaMatrix1337 {
    def kWeakestRows(mat: Array[Array[Int]], k: Int): Array[Int] = {

        import scala.collection.mutable.PriorityQueue

        val ordering: Ordering[(Int, Int)] = Ordering.fromLessThan[(Int, Int)]((a, b) => {
            if (b._1 == a._1) b._2 < a._2
            else b._1 < a._1
        })
        val minHeap: PriorityQueue[(Int, Int)] = PriorityQueue[(Int, Int)]()(ordering)

        val solCount = mat.zipWithIndex.foldLeft(minHeap) {
            case (accum, (row, idx)) => 
                val count = row.count(_ == 1)
                accum.enqueue((count, idx))
                accum
        }

        val ans = solCount.dequeueAll
        val idx = ans.map(_._2)

        idx.toArray
    }

    def main(args: Array[String]): Unit = {

        println(kWeakestRows(Array(
            Array(1,1,0,0,0),
            Array(1,1,1,1,0),
            Array(1,0,0,0,0),
            Array(1,1,0,0,0),
            Array(1,1,1,1,1)
        ), 3).mkString(" "))
    }
}