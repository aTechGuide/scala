package in.kamranali.AdvanceTypeSystem

/**
  * Advance Scala Lesson 44 [Variance]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053854
  */

object Variance extends App {

  trait Animal

  class Dog extends Animal
  class Cat extends Animal
  class Crocodile extends Animal

  /**
    What is variance
    - Its a problem of "inheritance", in reality its the problem of Type Substitution of Generics
   */

  class Cage[T]

  /*
    We know Cat inherit Animal, QUESTION is should Cage[Cat] inherit Cage[Animal]
   */

  // 1 If answer is "YES" this is Covariance
  class CCage[+T]

  val ccage: CCage[Animal] = new CCage[Cat] //<- We are replacing general cage of Animal with specific cage of Cat

  // 2 If answer is "NO" this is Invariance
  class ICage[T]
  //val icage: ICage[Animal] = new ICage[Cat] // <- Compilation error; Similar to saying `val x: Int = "hello"`

  // 3 If answer is "HELL NO" - opposite = contravariance
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal] //<- We are replacing specific cage of CAT with general cage of Animal i.e. if RHS contain and Animal, it can also contain a Cat

  /**
   * Before Going into details learn GOLDEN RULE
   * - If our generic type Contains or Creates or Produces elements of type T, it should be +T.
   *   - E.g. Cage (Holding animals), Garage (Holding Cars), Factory (Producing elements), List (Holding elements)
   * - If our generic type Consumes or Acts on elements of type T, it should be -T.
   *   - E.g. Vet (Heals animals), Mechanic (Fix Cars), Garbage Pit (Consumes Object), function (in terms of argument Type, as it acts on elements)
   */

  /**
    Various Type of Animal Cages with an Animal inside
   */

  /**
    FIELDS (val)
    - Generic Type of `vals` in Fields accepts Invariant Types (Point 1)
    - Generic Type of `vals` in Fields are in a COVARIANT POSITION (Point 2)
   */

  // Point 1
  class InvariantCage[T](val animal: T) //<- Compiler accepts Invariant type in `animal: T` [Even though it is a COVARIANT Position]

  // Point 2 [covariant positions]
  class CovariantCage[+T](val animal: T) //<- Generic Type of `val` (of fields) are in a COVARIANT POSITION i.e. compiler accept animal with Covariant Type
  val ccagef1: CovariantCage[Animal] = new CovariantCage[Cat](new Cat)

  class SuperCat extends Cat
  val ccagef2: CovariantCage[Animal] = new CovariantCage[Cat](new SuperCat)


  // Point 3
  /*
     class ContravariantCage[-T](val animal: T) // <- Compilation Error, "contravariant type T occurs in covariant position in type => T of value animal"

     If above code would have compiled it means we can write
       val catCage: CovariantCage[Cat] = new CovariantCage[Animal](new Crocodile) as `Crocodile` extends `Animal`.

     Problem is we wanted specific cage of CAT but we are filling it with `Crocodile`
   */

  /**
    FIELDS (var)
    - Generic Type of `var` in Fields are in a CONTRAVARIANT POSITION (Point 1)
    - Generic Type of `var` in Fields are in a COVARIANT POSITION as well (Point 2)

    Hence, since `var` are in CONTRAVARIANT and COVARIANT POSITION. Only acceptable type for `var` is INVARIANT (Point 3)
 */

  // Point 1
  /*
     class CovariantVariableCage[+T](var animal: T) //<- Compilation Error, "Covariant type T occurs in contravariant position in type T of value animal"
     Types of VARS are in CONTRAVARIANT POSITION

     If above code would have compiled it means we can write

     val ccage: CovariantVariableCage[Animal] = new CovariantVariableCage[Cat](new Cat)
     ccage.animal = new Crocodile [Problematic]
   */

  // Point 2
  /*
      class ContraVariantVariableCage[-T](var animal: T) //<- Compilation Error, "contravariant type T occurs in covariant position in type => T of variable animal"
      Here `var animal: T` is in covariant position

      val catCage: ContraVariantVariableCage[Cat] = new ContraVariantVariableCage[Animal](new Crocodile) [Problematic]
   */

  // Point 3
  class InvariantVariableCage[T](var animal: T) //<- Compiles

  /**
    METHOD ARGUMENTS
    - METHOD ARGUMENTS i.e. `(animal: T)` are in CONTRAVARIANT Position (Point 2)
  */
  // Point 1
  trait AnotherCovariantCage[+T] {
    //def addAnimal(animal: T) //<- Compilation Error: "Covariant type T occurs in contravariant position in type T of value animal"
    /*
        It is same as saying

        var ccage: AnotherCovariantCage[Animal] = new AnotherCovariantCage[Dog]
        ccage.add(new Cat)
     */
  }

  // Point 2
  class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true // <- Compiles
  }

  // Hence we can say
  val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat) //<- we can only pass `CAT` or its super type as method arguments. And NOT Dog i.e. `acc.addAnimal(new Dog)` will NOT compile

  class Kitty extends Cat
  acc.addAnimal(new Kitty)

  // That is sad/painful problem because we often want to create a COVARIANT Collection. But then we can NOT add elements to them
  // We can solve this problem by following technique
  class MyList[+A] {
    // def add(element: A): MyList[A] //<- Compilation Error[covariant type A occurs in contravariant position in type A]
    def add[B >: A](element: B): MyList[B] = new MyList[B]  // Hacking Compiler by a Technique known as "WIDENING the TYPE"
    // `B >: A` => B is super Type of A
  }

  val emptyList: MyList[Kitty] = new MyList[Kitty]
  val animals: MyList[Kitty] = emptyList.add(new Kitty)
  val moreAnimals: MyList[Cat] = animals.add(new Cat) // Compiler is happy because Cat is super type of Kitty (B >: A). So compiler will return a List of `Cat`
  val evenMoreAnimals: MyList[Animal] = moreAnimals.add(new Dog) // Compiler will take Dog as an Animal (which is next lowest ancestor of both Cat and Dog). Compiler has WIDENED the return Type of `evenMoreAnimals` to `Animal`

  //Rule: Basically we want to keep the property that all the animals in the list have a common type

  /**
    RETURN TYPES
    - Method Return Types are in COVARIANT Position
   */

  class PetShop[-T] {
    /*
      def get(isItaPuppy: Boolean): T //<- Compilation error: "Contravariant type T occurs in covariant position in type T of value get"

      If above code compiles it will mean

      val catShop = new PetShop[Animal] {
          get(isItaPuppy: Boolean): Animal = new Cat
        }

      val dogShop: PetShop[Dog] = catShop
      dogShop.get(true) // EVIL CAT
     */

    // To make above code compile we can do following hack
    def get[S <: T](isItaPuppy: Boolean, defaultAnimal: S): S = defaultAnimal
    // S <: T => S is subtype of T
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
  //val evilCat = shop.get(true, new Cat) //<- Compilation Error

  class TerraNova extends Dog
  val bigFurry = shop.get(isItaPuppy = true, new TerraNova) // Compiles


  /**
    BIG RULES
    - Method Arguments are in CONTRAVARIANT Position
    - Return Types are in COVARIANT Position
   */
}
