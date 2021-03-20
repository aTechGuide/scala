package in.kamranali.fp

/**
  * Advance Scala Lesson 18 [Monads]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/10937404
  */

/**
    Monads are kind of types (abstract types) which have some fundamental operation.

    They have two operations
    - def unit(value: A): MonadTemplate[A] // <- Known as Pure or apply
    - def flatMap[B](f: A => MonadTemplate[B]): MonadTemplate[B] // <- Also known as bind

    List, Option, Try, Future, Stream, Set are all monads
   */

  /**
    Monad LAWS:

      - Left Identity:  unit(x).flatMap(f) == f(x)
      - Right Identity: aMonad.flatMap(unit) == aMonad
      - Associativity: aMonad.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
   */

  // Let's see how these Monad Laws apply to List

  /*
    /** Left Identity **/

      List(x).flatMap(f) =
        f(x) ++ Nil.flatMap(f) =
          f(x)

    /** Right Identity **/

      list.flatMap(x => List(x)) = list

   /** Associativity **/

    [a b c].flatMap(f).flatMap(g) =
      (f(a) ++ f(b) ++ f(c)).flatMap(g) =
        f(a).flatMap(g) ++ f(b).flatMap(g) ++ f(c).flatMap(g) =
          [a b c].flatMap(f(_).flatMap(g)) =
            [a b c].flatMap(x => f(x).flatMap(g))
 */

  // Let's see how these Monad Laws apply to Option

  /*
    /** Left Identity **/

      Option(x).flatMap(f) =
        Some(x).flatMap(f) =
          f(x)

    /** Right Identity **/

      opt.flatMap(x => Option(x)) = opt
      Some(v).flatMap(x => Option(x)) = Option(v) = Some(v)

   /** Associativity **/

    o.flatMap(f).flatMap(g) =  o.flatMap(x => f(x).flatMap(g))

    LHS
    Some(v).flatMap(f).flatMap(g) = f(v).flatMap(g)

    RHS
    Some(v).flatMap(x => f(x).flatMap(g)) = f(v).flatMap(g)


 */

object Monads extends App {

  // Our own try monad
  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }

  object Attempt {
    // We used call by name argument because we don't want this argument to be evaluated when we build the attempt.
    // Because the evaluation of the parameter might throw exceptions and want to prevent that
    def apply[A](a: => A): Attempt[A] =
      try {
        Success(a) // trying to build a success will evaluate that parameter a
      } catch {
        case e: Throwable => Fail(e)
      }
  }

  case class Success[+A](value: A) extends Attempt[A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B] =
      try {
        f(value)
      } catch {
        case e: Throwable => Fail(e)
      }
  }

  case class Fail(e: Throwable) extends Attempt[Nothing] {
    def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this // returns the same failure
  }

  /*
    Left Identity

      unit.flatMap(f) = f(x)

      Attempt(x).flatMap(f) = f(x) // Success
      Success(x).flatMap(f) = f(x) // Proof
   */

  /*
    Right Identity

      attempt(x).flatMap(unit) = attempt

      Success(x).flatMap(x => Attempt(x)) = Attempt(x) = Success(x)
      Fail(_).flatMap(...) = Fail(e)
   */

  /*
    Associativity

      attempt.flatMap(f).flatMap(g) == attempt.flatMap(x => f(x).flatMap(g))

      Fail(e).flatMap(f).flatMap(g) = Fail(e)
      Fail(e).flatMap(x => f(x).flatMap(g)) = Fail(e)

      Success(v).flatMap(f).flatMap(g) =
        f(v).flatMap(g) OR Fail(e)

      Success(v).flatMap(x => f(x).flatMap(g)) =
        f(v).flatMap(g) OR Fail(e)

   */

  val attempt = Attempt {
    throw new RuntimeException("My Monad")
  }

  println(attempt) // Fail(java.lang.RuntimeException: My Monad)

    /*
    EXERCISE:

    1) Implement a Lazy[T] monad = computation which will only be executed when it's needed.
      unit/apply
      flatMap

    2) How would you implement map and flatten, If I gave you a complete and correct implementation of flatMap

       Monads = unit + flatMap
       Monads = unit + map + flatten
       Monad[T] {
        def flatMap[B](f: T => Monad[B]): Monad[B] = ... (Given implemented)

        Implement following
        def map[B](f: T => B): Monad[B] = ???
        def flatten(m: Monad[Monad[T]]): Monad[T] = ???
   */

  /**
    Lazy Monad
    - Computation which will only be executed when it's needed.
   */
  class Lazy[+A](value: => A) {

    // Call by Need
    private lazy  val internalValue = value// <- This prevents evaluation of lazy variable twice

    def use: A = internalValue
    def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(internalValue)
    // Receiving parameter by Name (=> A) is important
    // Which will delay the evaluation of value inside F because f receives the parameter by name
  }

  object Lazy {
    def apply[A](value: => A): Lazy[A] = new Lazy(value) // <- This is equal to Unit
  }

  val lazyInstance = Lazy {
    println("I'm lazy")
    42
  }

  // println(lazyInstance.use) // <- it will print "I'm lazy" + 42

  val flatMappedInstance1 = lazyInstance.flatMap(x => Lazy {
    10 * x
  })

  val flatMappedInstance2 = lazyInstance.flatMap(x => Lazy {
    10 * x
  })

  flatMappedInstance1.use
  flatMappedInstance2.use

  /*
    Left Identity: Lazy(v).flatMap(f) = f(v)
    Right Identity: Lazy(v).flatMap(x => Lazy(x)) = Lazy(v)
    Associativity:
     - Lazy(v).flatMap(f).flatMap(g) = f(v).flatMap(g)
     - Lazy(v).flatMap(x => f(x).flatMap(g)) = f(v).flatMap(g)
   */

    // 2: map and flatten in terms  of flatMap
    /*
      Monad[T] { // Think of it as List
        def flatMap[B](f: T => Monad[B]): Monad[B] = ... (implemented)

        def map[B](f: T => B): Monad[B] = flatMap(x => unit(f(x)) ) // Monad[B]

        def flatten(m: Monad[Monad[T]]): Monad[T] = m.flatMap((x: Monad[T]) => x)

        List(1,2,3).map(_ * 2) = List(1,2,3).flatMap(x => List(x * 2))
        List(List(1, 2), List(3, 4)).flatten = List(List(1, 2), List(3, 4)).flatMap(x => x) = List(1,2,3,4)
      }
     */

}
