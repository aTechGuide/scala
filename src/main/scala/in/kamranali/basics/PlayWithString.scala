package in.kamranali.basics

object PlayWithString extends App{

  // S - Interpolators
  val name = "Kamran"
  val age = 12

  val greeting = s"Hello my name is $name & I will be turning ${age + 1} years old"

  // println(greeting)

  // F - Interpolators
  val speed = 1.24356f
  val myth = f"$name can run at $speed%2.3f m/s" // 2 chars total minumum and 3 digits precision

  // println(myth)

  // raw - Interpolators
  // println(raw"This is a \n newline") // OP: prints "This is a \n newline"

  // But
  val escaped = "This is a \n newline"
  println(raw"$escaped")
  // here `\n` escapes line

}
