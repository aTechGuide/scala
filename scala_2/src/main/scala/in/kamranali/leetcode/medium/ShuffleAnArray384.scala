package in.kamranali.leetcode.medium

class ShuffleAnArray384(_nums: Array[Int]) {

    val original = _nums
    val r = new scala.util.Random

    /** Resets the array to its original configuration and return it. */
    def reset(): Array[Int] = {
        original
    }

    /** Returns a random shuffling of the array. */
    def shuffle(): Array[Int] = {

        val shuffled = original.clone

        def s(idx: Int): Unit = {

            if (idx == original.length) ()
            else {
                val rIdx = r.nextInt(original.length)

                val temp = shuffled(idx)
                shuffled(idx) = shuffled(rIdx)
                shuffled(rIdx) = temp
              s(idx + 1)
            }
        }

        s(0)
        shuffled
    }
}

object ShuffleanArray384Spec {
  def main(args: Array[String]): Unit = {
    val input = Array(1, 2, 3)
    val algo = new ShuffleAnArray384(input)

    println(algo.shuffle().mkString(" "))
  }
}