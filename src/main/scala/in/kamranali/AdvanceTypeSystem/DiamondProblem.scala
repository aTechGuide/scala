package in.kamranali.AdvanceTypeSystem

object DiamondProblem extends App {

  /*
   Diamond Problem
  */

  trait Animal1 {def name: String}
  trait Lion1 extends Animal1 { override def name: String = "Lion"}
  trait Tiger1 extends Animal1 { override def name: String = "Tiger"}

  // trait Mutant1 extends Lion1 with Tiger1 //<- Compile because `name` is UNimplemented

  class Mutant1 extends Lion1 with Tiger1 { //<- Have to provide implementation of `name`
    override def name: String = "ALIEN"
  }

  trait Animal2 {def name: String}
  trait Lion2 extends Animal2 { override def name: String = "Lion"}
  trait Tiger2 extends Animal2 { override def name: String = "Tiger"}

  class Mutant2 extends Lion2 with Tiger2 //<- Compiles even without an implementation to `name`; "DIAMOND PROBLEM"

  val mutant = new Mutant2
  println(mutant.name) // prints "Tiger"

  // It prints "Tiger" because compiler converts above line to
  // Mutant2 extends Animal2 with { override def name: String = "Lion"} with Animal2 { override def name: String = "Tiger"}
  // hence using last definition of name method.
  // LAST OVERRIDE ALWAYS GETS PICKED

}
