package in.kamranali.implicits

import java.{util => ju}

/**
  * Advance Scala Lesson 41 [Scala Java Conversions]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053846
  */

object ScalaJavaConversions extends App {

  /**
    JAVA to SCALA
   */
  import collection.JavaConverters._

  val javaSet: ju.Set[Int] = new ju.HashSet[Int]()

  (1 to 5).foreach(javaSet.add)

  println(javaSet) // [1, 2, 3, 4, 5]

  val scalaSet = javaSet.asScala

  /*
    Available Converters
    - Iterator
    - Iterable
    - ju.List - scala.mutable.Buffer
    - ju.Set - scala.mutable.Set
    - ju.Map - scala.mutable.Map
   */

  /*
    SCALA To JAVA
   */

  import collection.mutable._

  val numbersBuffer = ArrayBuffer[Int](1,2,3)

  val juNumberBuffer = numbersBuffer.asJava

  // Side Note: if we convert `juNumberBuffer` back to `numbersBuffer`, We will get exact reference to old `numbersBuffer`
  println(juNumberBuffer.asScala eq numbersBuffer) // <- Reference equality with shallow `eq` // prints true


   // IMPORTANT: Some Scala to Java conversion can NOT give same Reference
  val numbers = List(1,2,3) // Immutable List
  val juNumbers = numbers.asJava // Mutable List
  val backToScala: Buffer[Int] = juNumbers.asScala // Conversion of Mutable List to Mutable Buffer

  println(numbers eq backToScala) //false
  println(numbers == backToScala) //true

  // CAVEATS to Immutable (Scala) to Mutable (JAVA) Conversion
  // we can say
  // juNumbers.add(1) // <- Compiles + Throws an exception, Because Java Does NOT know its an Immutable list

  /**
    Exercise: Scala to Java Conversion between Optional to option
   */

  class ToScala[T](value: => T) {
    def asScala: T = value
  }

  implicit def asScalaOptional[T](o: ju.Optional[T]): ToScala[Option[T]] = new ToScala[Option[T]](
    if (o.isPresent) Some(o.get()) else None
  )

  val juOptional: ju.Optional[Int] = ju.Optional.of(2)
  val scalaOptional = juOptional.asScala

  println(scalaOptional) // Some(2)



}
