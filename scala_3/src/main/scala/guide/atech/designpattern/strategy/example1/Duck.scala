package guide.atech.designpattern.strategy.example1

import guide.atech.designpattern.strategy.example1.behaviour.FlyBehaviour

// Define behaviours via Strategy Pattern
abstract class Duck(var fly: FlyBehaviour) {
  
  def swim(): Unit
  def display(): Unit
  
  def performFly(): Unit = fly.fly()
  def setFlyBehaviour(newFlyBehaviour: FlyBehaviour): Unit = {
    this.fly = newFlyBehaviour
  }
}
