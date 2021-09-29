package in.kamranali.lm.multithreading

import scala.util.Random

object MultiReaderWriter5 {

  case class Buffer(var idx: Int, len: Int) {
    val arr: Array[Int] = Array.ofDim[Int](len)
  }

  class Reader(buffer: Buffer) extends Runnable {
    override def run(): Unit = {

      while (true) {
        buffer.synchronized {
          while (buffer.idx == -1) {
            buffer.wait()
          }

          println(s"Reader Consumed ${buffer.arr(buffer.idx)} at index ${buffer.idx}")
          buffer.idx = buffer.idx - 1

          buffer.notifyAll()
        }
      }
    }
  }

  class Writer(buffer: Buffer) extends Runnable {
    override def run(): Unit = {

      val random = new Random

      while (true) {
        buffer.synchronized {
          while (buffer.idx == buffer.len - 1) {
            buffer.wait()
          }

          val someData = random.nextInt(100)
          buffer.idx = buffer.idx + 1
          buffer.arr(buffer.idx) = someData

          println(s"Writer Produced $someData at index ${buffer.idx}")

          buffer.notifyAll()
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val data = Buffer(-1, 5)

    new Thread(new Writer(data)).start()
    new Thread(new Reader(data)).start()
  }

}
