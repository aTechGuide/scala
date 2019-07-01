package in.kamranali.AdvanceTypeSystem

object InheritanceWithConvenience extends App {

  // look into trait mixions

  /*
    Convenience
   */

  // Let's define Minimal API
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closable {
    def close(status: Int): Unit
  }

  trait GenericStream[T] {
    def foreach(f: T => Unit): Unit
  }

  // Convenience => If we have GenericStream which mixes with other traits lets say Closable + Writer we can say,
  def processStream[T](stream: GenericStream[T] with Writer[T] with Closable) = { //<- `GenericStream[T] with Writer[T] with Closable` is its own Type
    stream.foreach(println)
    stream.close(1)
  }
}
