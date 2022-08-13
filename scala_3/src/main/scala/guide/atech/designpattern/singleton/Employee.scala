package guide.atech.designpattern.singleton

class Employee private (val name: String, val age: Int)

object Employee {

  var instance: Option[Employee] = None

  def apply(name: String, age: Int): Employee = {
    if (instance.nonEmpty) instance.head
    else {
      // Double check locking mechanism
      this synchronized {
        if (instance.nonEmpty) instance.head
        else {
          instance = Some(new Employee(name, age))
          instance.head
        }
      }
    }
  }
}