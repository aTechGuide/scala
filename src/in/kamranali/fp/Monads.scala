package in.kamranali.fp

/*
Monads are kind of types (abstract types) which have some fundamental operation.

They have two operations
- def Unit(value: A): MonadTemplate[A] // <- Known as Pure or apply
- def flatMap[B](f: A => MonadTemplate[B]): MonadTemplate[B] // <- known as bind

List, Optio, Try, Future, Stream, Set are all monads
 */

/*
Monad LAWS:

- Left Identity:  unit(x).flatMap(f) == f(x)
- Right Identity: aMonad.flatMap(unit) == aMonad
- Associativity: aMonad.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
 */

// Let's see how these Monad Laws apply to List

/*
Left Identity

List(x).flatMap(f) =
  f(x) ++ Nil.flatMap(f) =
    f(x)
 */

/*
Right Identity

list.flatMap(x => List(x)) = list
 */

/*
Associativity

[a b c].flatMap(f).flatMap(g) =
  (f(a) ++ f(b) ++ f(c)).flatMap(g) =
    f(a).flatMap(g) ++ f(b).flatMap(g) ++ f(c).flatMap(g) =
      [a b c].flatMap(f(_).flatMap(g)) =
        [a b c].flatMap(x => f(x).flatMap(g))
 */

object Monads extends App {

  // Our own try monad
  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }

  object Attempt {
    def apply[A](a: => A): Attempt[A] =
      try {
        Success(a)
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

  Attempt(x).flatMap(f) = f(x) // Success
  Success(x).flatMap(f) = f(x) // Proof
   */

  /*
  Right Identity

  attempt(x).flatMap(unit) = attempt
  Success(x).flatMap(x => Attempt(x)) = Attempt(x) = Success(x)
  Fail(e).flatMap(...) = Fail(e)
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

  println(attempt)

  /*
  Lazy Monad
   */
  class Lazy[+A](value: => A) {

    // Call by Need
    private lazy  val internalValue = value// <- This prevents evaluation of lazy variable twice

    def use: A = internalValue
    def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(internalValue) // Receiving parameter by Name (=> A) is important
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
  Right Identity: Lazy(v).flatMap(x => Lazy(x)) = Lazy(y)
  Associativity:
   - Lazy(v).flatMap(f).flatMap(g) = f(v).flatMap(g)
   - Lazy(v).flatMap(x => f(x).flatMap(g)) = f(v).flatMap(g)
   */

}
