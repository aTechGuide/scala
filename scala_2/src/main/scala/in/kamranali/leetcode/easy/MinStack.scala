package in.kamranali.leetcode.easy

class MinStack() {

    /** initialize your data structure here. */
  // formula,
  // Push = 2 * element - minEle
  // Pop = 2 * minEle - eleOnStack
    
    var dataStack = List.empty[Long]
    var min: Long = Int.MinValue
    

    def push(`val`: Int): Unit = {
        
       if (dataStack.isEmpty) {
          // first element
         min = `val`
         dataStack = `val` :: dataStack
       } else if (`val` >= min) {
         dataStack = `val` :: dataStack
       } else {
         // new element is less than min
         
         val toPush = 2L * `val` - min
         dataStack = toPush :: dataStack
         min = `val`
       } 
    }

    def pop(): Unit = {
        
      val elem = dataStack.head
      dataStack = dataStack.tail
      
      if (elem < min) {
        min = 2 * min - elem
      } 
    }

    def top(): Int = {
      val elem = dataStack.head
      
      if (elem >= min) {
        elem.toInt
      } else {
        min.toInt
      }
    }

    def getMin(): Int = min.toInt

}

object MinStackTest {
  def main(args: Array[String]): Unit = {

    val st = new MinStack()

    st.push(2147483647)
    st.push(-2147483648)

    println(st.top())

  }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * var obj = new MinStack()
 * obj.push(`val`)
 * obj.pop()
 * var param_3 = obj.top()
 * var param_4 = obj.getMin()
 */

object testingString extends App {
  val data = "    a good   example"
  println(data.split(" ").filter(_.nonEmpty). mkString(" "))
}