package in.kamranali.dsl

/**
  * Composing Email filter using simple, orthogonal building blocks.
  *
  * A functional DSL consists of three components:
  * - Types that describe solutions
  * - Constructors for those types, that give the simplest possible solutions
  * - Operators that compose or transform solutions
  *
  * Reference
  * - https://medium.com/bigdatarepublic/writing-functional-dsls-for-business-domains-1bccc5d3f62b
  */

/**
  * Design Principles
  * There are many ways to factor a DSL, but some are better than others.
  * These guiding principles help come to a good design. Our components should be
  *
  * - Composable —> to build complex solutions using simple components;
  * - Orthogonal —> such that there’s no overlap in capabilities between primitives (i.e. MECE or the single-responsibility principle);
  * - Minimal —> in terms of the number of primitives.
  */

/**
  * Types
  *
  * - There should be a single type that describes a solution to a domain problem.
  * - In our case that’s the EmailFilter trait, that describes solutions to the domain problem of filtering emails.
  */

final case class Address(email: String)
final case class Email(sender: Address, to: List[Address], subject: String, body: String)

sealed trait EmailFilter { // self: EmailFilter =>

  import EmailFilter._

  /**
    * Operators
    *
    * Operators can combine and transform the data structures in order to Lego together more complex solutions.
    * Like constructors, operators can be either primitive, like negate, or derived, like ||.
    */

  def &&(that: EmailFilter): EmailFilter = EmailFilter.And(this, that)
  def negate: EmailFilter = EmailFilter.Not(this)
  def ||(that: EmailFilter): EmailFilter = (this.negate && this.negate).negate

  def run(email: Email): Boolean = this match {
    case Always => true
    case Not(filter: EmailFilter) => !filter.run(email)
    case And(left: EmailFilter, right: EmailFilter) => left.run(email) && right.run(email)
    case SenderEquals(target: Address) => target == email.sender
    case RecipientEquals(target: Address) => email.to.contains(target)
    case BodyContains(phrase: String) => email.body.contains(phrase)
  }
}

object EmailFilter {

  case object Always                                              extends EmailFilter
  final case class Not(filter: EmailFilter)                       extends EmailFilter
  final case class And(left: EmailFilter, right: EmailFilter)     extends EmailFilter

  final case class SenderEquals(target: Address)                  extends EmailFilter
  final case class RecipientEquals(target: Address)               extends EmailFilter
  final case class BodyContains(phrase: String)                   extends EmailFilter

  /**
    * Constructors
    *
    * The constructors for these types create the simplest possible solutions.
    * For example, bodyContains or recipientIn.
    *
    * - Constructors that use only one case class are called primitive constructors.
    * - Derived constructors, like senderIsNot, use a combination of constructors and operators.
    */

  val always: EmailFilter = Always
  val never: EmailFilter = always.negate

  def senderIs(sender: Address): EmailFilter = SenderEquals(sender)
  def senderIsNot(sender: Address): EmailFilter = SenderEquals(sender).negate

  def bodyContains(phrase: String): EmailFilter = BodyContains(phrase)
  def bodyDoesNotContains(phrase: String): EmailFilter = BodyContains(phrase).negate

}



object FilteringEmails extends App {

  val filter: EmailFilter = EmailFilter.senderIsNot(Address("me@gmail.com")) && EmailFilter.bodyContains("Unsubscribe")
  val emails: List[Email] = List(Email(Address("info@someretailer.com"), List(Address("me@gmail.com")), "Promotion", "10% off. Unsubscribe"))

  val newsletters = emails.filter(email => filter.run(email))
  println(newsletters)

}
