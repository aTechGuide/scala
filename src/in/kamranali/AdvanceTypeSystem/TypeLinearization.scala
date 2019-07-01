package in.kamranali.AdvanceTypeSystem

object TypeLinearization extends App {

  /*
    Type Linearization
   */

  trait Cold {
    def print = println("cold")
  }

  trait Green extends Cold {
    override def print: Unit = {
      println("green")
      super.print
    }
  }

  trait Blue extends Cold {
    override def print: Unit = {
      println("blue")
      super.print
    }
  }

  trait Red {
    def print = println("red")
  }

  class White extends Red with Green with Blue {
    override def print: Unit = {
      println("white")
      super.print
    }
  }

  val color = new White // Prints: white -> Blue -> Green -> Cold
  color.print

  /*
    White = Red with Green with Blue with <white>
      = AnyRef with <Red>
        with (AnyRef with <Cold> with <Green>)
        with (AnyRef with <Cold> with <Blue>)
        with <white>
      = AnyRef with <Red> with <Cold> with <Green> with <Blue> with <white>

      with each call to `super` we jump from right to left
   */


}
