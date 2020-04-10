package in.kamranali.basics

object Variables extends App {

  /*
  Variables
   */
  val x = 10
  //println(x) // prints 10

  // x = 2 val can't be reassigned as they are immutable
  // val x = 10; Types of val are optional as Compiler can infer the type of x from RHS

  // Write each expression on its own line avoid using semicolon (;)

  // Types => String, Boolean, Char, Int (4 Byte), Short (2 Byte), Long (8 Byte), Float, Double

  // To define a variable
  var variable: Int = 5
  //print(variable)

  variable = 6 // Allowed, Remember Variables have side effects
  // Functional Programming says, "work less with variables"

  /*
  Expressions
   */

  val y = 1 + 2 // 1 + 2 is called expression; Expression are evaluated to a value (i.e. 3) and have a type (i.e. Int)
  // Operators: + - * / & | ^ << >> >>>

  // print(1 == x)
  // == != > >= < <= += -= *= /=

  // print(!(1 == x))
  // ! && ||


  // Instructions (This is what we tell the computer to do) Vs Expresssions (It has a Value Or a Type)

  // In Scala we have If Expression
  val condValue = if(true) 3 else 5 // If EXPRESSION gives back a value. That's why we say it If expression not If instruction
  //print(condValue) // prints 3

  /*
  LOOPS
   */

  // Looping is very specific to imperative programming e.g.
  var i = 0
  while (i < 5) {
    //print(i)
    i += 1
  }

  // DON't Write Above Loop

  /*
  Expressions Vs Instructions
  1. Instructions are executed (it says do something;think JAVA), expressions are evaluated (it says return me a value if something; think Scala)
   */

  // Everything in scala is an Expression
  var aVariable = 3
  val weird: Unit = (aVariable = 3) // Type of weird is Unit, which is a special type in scala equivalent to void in other languages

  print(weird) //OP: ()

  // Side effects in scala are expressions returning Unit. When we say, Reassigning a variable is a side effect it means it is returning Unit.
  // Side Effects: print(), whiles, reassigning

  val awhile: Unit = while (i < 5) { i += 1 }

  // Side effects are remnants of imperative programming. They are like instructions but in scala they are Expressions returning Units

  /*
  CODE BLOCKS: Special Kind of expression with special properties
   */

  val aCodeBlock = {
    val y = 2
    val z = y + 2 // y,z are not visible outside

    if (z > 2) "hello" else "GoodBye"
  }

  // Facts of blocks
  // 1. Code block is an expression
  // 2. Value of block is value of last expression; Value of block 80:85 is value of "if (z > 2) "hello" else "GoodBye"" (i.e. String) as it occurs last

}
