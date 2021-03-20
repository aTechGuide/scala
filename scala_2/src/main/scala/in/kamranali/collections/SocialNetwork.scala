package in.kamranali.collections

import scala.annotation.tailrec

/**
  * Basic Scala Lesson 32 [Tuples And Maps Exercise]
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/10371102#content
  */

object SocialNetwork extends App {

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {

    network + (person -> Set()) // network + newPair
  }

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {

    val friendA: Set[String] = network(a)
    val friendB: Set[String] = network(b)

    network + (a -> (friendA + b)) + (b -> (friendB + a))
  }

  def unFriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {

    val friendA = network(a)
    val friendB = network(b)

    network + (a -> (friendA - b)) + (b -> (friendB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {

    @tailrec
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = {
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unFriend(networkAcc, person, friends.head))
    }

    val unFriended = removeAux(network(person), network) // remove the friendship
    unFriended - person // removes person key

  }

  def nFriends(network: Map[String, Set[String]], person: String): Int = {
    if (!network.contains(person)) 0
    else network(person).size
  }

  def mostFriends(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1
  }

  def nPeopleWithNoFriend(network: Map[String, Set[String]]): Int = {
    network.filterKeys(network(_).isEmpty).size
    // network.count(_._2.isEmpty) //<- Alternative Implementation
  }

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {

    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }

    bfs(b, Set(), network(a) + a)
  }

  // Testing
  val empty: Map[String, Set[String]] = Map()

  val network = add(add(empty, "Bob"), "Mary")
  println(network) // Map(Bob -> Set(), Mary -> Set())

  println(friend(network, "Bob", "Mary")) // Map(Bob -> Set(Mary), Mary -> Set(Bob))

  println(unFriend(network, "Bob", "Mary")) // Map(Bob -> Set(), Mary -> Set())

  println(remove(friend(network, "Bob", "Mary"), "Bob")) // Map(Mary -> Set())

  println("\n\n /** People Network **/")
  // Jim, Bob, Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet) // Map(Bob -> Set(Jim, Mary), Mary -> Set(Bob), Jim -> Set(Bob))
  println(nFriends(testNet, "Bob")) // 2
  println(mostFriends(testNet)) // Bob
  println(nPeopleWithNoFriend(testNet)) // 0

  println(socialConnection(testNet, "Mary", "Jim")) // true
  println(socialConnection(network, "Bob", "Mary")) // false

}
