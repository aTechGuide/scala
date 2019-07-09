package in.kamranali.AdvanceTypeSystem

object VarianceExercise extends App {

  /*
    Design Invariant, covariant, contravariant Parking of things
    - CLASS: Parking[T](things: List[T])
    - Methods:
      - park(vehicle: T)
      - impound(vehicles: List[t])
      - checkVehicles(conditions: String): List[T]

    Make parking a Monad
    - Add flatMap Method
   */

  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle

  class IList[T]

  // Invariant Version
  class IParking[T](vehicles: List[T]) {
    def park(vehicle: T): IParking[T] = ???
    def impound(vehicles: List[T]): IParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  // Covariant Version
  class CParking[+T](vehicles: List[T]) {
    def park[S >: T](vehicle: S): CParking[S] = ???
    def impound[S >: T](vehicles: List[S]): CParking[S] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => CParking[S]): CParking[S] = ???
  }

  // Contravariant Version
  class XParking[-T](vehicles: List[T]) {
    def park(vehicle: T): XParking[T] = ???
    def impound(vehicles: List[T]): XParking[T] = ???
    def checkVehicles[S <: T](conditions: String): List[S] = ???

    def flatMap[R <: T, S](f: R => XParking[S]): XParking[S] = ???
  }

  /*
    RULE OF THUMB:
      - Use Covariance: COLLECTION OF THINGS e.g. Parking as Collection of Vehicles
      - Use Contravariance: GROUP OF ACTIONS e.g. Parking as collection of Actions that we want to perform on Type
   */

  // OUR Types

  // Covariant Version
  class CParking2[+T](vehicles: IList[T]) {
    def park[S >: T](vehicle: S): CParking2[S] = ???
    def impound[S >: T](vehicles: IList[S]): CParking2[S] = ???
    def checkVehicles[S >: T](conditions: String): IList[S] = ???
  }

  class XParking2[-T](vehicles: IList[T]) {
    def park(vehicle: T): XParking2[T] = ???
    def impound[S <: T](vehicles: IList[S]): XParking2[S] = ???
    def checkVehicles[S <: T](conditions: String): IList[S] = ???
  }

  // Flat Maps
}
