package guide.atech.designpattern.strategy.example1

import guide.atech.designpattern.strategy.example1.behaviour.FlyBehaviour

class MallardDuck(flyBehaviour: FlyBehaviour) extends Duck(flyBehaviour) {
  override def swim(): Unit = println(s"Mallard Swim")
  override def display(): Unit = println(s"Mallard Display")
}
