package in.kamranali.leetcode.medium

object RottingOranges994 {
  case class Item(x: Int, y: Int, t: Int)
  
  def orangesRotting(grid: Array[Array[Int]]): Int = {
    import scala.collection.mutable.Queue
    
    val rows = grid.length
    val cols = grid(0).length
    
    val q = Queue.empty[Item]
    var countFresh = 0
    
    // Add all the rotten oranges to queue to start AND count all the fresh Oranges
    (0 until rows).foreach {r => {
      (0 until cols).foreach {c => {
        
        if (grid(r)(c) == 2) {
          q.enqueue(Item(r, c, 0))
        } else if (grid(r)(c) == 1) {
          countFresh = countFresh + 1
        }
      }}
    }}
    
    val directions = Array(1->0, -1->0, 0->1, 0-> -1)
    
    // Auxiliary Functions
    def validCell(x: Int, y: Int): Boolean = (x >= 0 && x < rows) && (y >= 0 && y < cols)
    
    def calcTime(prevTime: Int, rottingCount: Int): Int = {
      if (q.isEmpty) 
        if (rottingCount != countFresh) -1
        else prevTime 
      else {
        val item = q.dequeue
        val t = item.t
        var nRottingCount = rottingCount
        
        directions
        .map( p => (p._1 + item.x) ->  (p._2 + item.y))
        .filter(p => validCell(p._1, p._2) && (grid(p._1)(p._2) == 1))
        .map(p => Item(p._1, p._2, t + 1))
        .foreach { item => 
          grid(item.x)(item.y) = 2 // Mark oranges as rotten
          nRottingCount = nRottingCount + 1
          q.enqueue(item) // Enqueue them 
        }
        
        calcTime(t, nRottingCount)
      }
    }
    
    // Driver
    val ans = calcTime(0, 0)
    ans
  }

  def main(args: Array[String]): Unit = {
    println(orangesRotting(Array(Array(2,1,1), Array(0,1,1), Array(1,0,1))))
  }
}