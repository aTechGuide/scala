package guide.atech.designpattern.strategy.example1.behaviour

class NoFly extends FlyBehaviour {
  def fly(): Unit = println(s"Can NOT Fly")
}
