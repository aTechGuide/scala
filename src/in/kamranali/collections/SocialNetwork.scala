package in.kamranali.collections

import scala.annotation.tailrec

object SocialNetwork extends App {

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {

    network + (person -> Set()) // network + newPair
  }

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {

    val friendA = network(a)
    val friendB = network(b)

    network + (a -> (friendA + b)) + (b -> (friendB + a))
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {

    val friendA = network(a)
    val friendB = network(b)

    network + (a -> (friendA - b)) + (b -> (friendB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {

    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = {
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))
    }

    val unfriended = removeAux(network(person), network) // remove the friendship
    unfriended - person // removes person key

  }

  def nFriends(network: Map[String, Set[String]], person: String): Int = {
    if (!network.contains(person)) 0
    else network(person).size
  }

  def mostFriends(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1
  }

  def nPeopleWithNoFriend(network: Map[String, Set[String]]): Int = {
    network.filterKeys(k => network(k).size == 0).size
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

  val empty: Map[String, Set[String]] = Map()

  val network = add(add(empty, "Bob"), "Mary")
  println(network)

  println(friend(network, "Bob", "Mary"))

  println(unfriend(network, "Bob", "Mary"))

  println("\n\n /** People Network **/")
  // Jim, Bob, Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)
  println(nFriends(testNet, "Bob"))
  println(mostFriends(testNet))
  println(nPeopleWithNoFriend(testNet))

  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(network, "Bob", "Mary"))

}
