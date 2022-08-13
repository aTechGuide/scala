package guide.atech.designpattern.singleton

object SingletonApp {

  def main(args: Array[String]): Unit = {
    val emp = Employee("kamran", 33)
    println(s"Emp(name=${emp.name},age=${emp.age})") // Emp(name=kamran,age=33)

    val emp2 = Employee("kamran", 35)
    println(s"Emp(name=${emp2.name},age=${emp2.age})") // Emp(name=kamran,age=33)
  }
}
