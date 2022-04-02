package in.kamranali.concurrency


/**
  * [Concurrency] Chapter 7: Future And Promises
  *
  * Dealing with Futures in Functional Way
  */

import scala.concurrent.Future
import scala.util.{Failure, Random, Success}

import scala.concurrent.ExecutionContext.Implicits.global

object Concurrency7_FuturesInFunctionalWay extends App {

  // Mini Social Network

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile): Unit = {
      println(s"${this.name} poking ${anotherProfile.name}")
    }
  }

  object SocialNetwork {

    // it has "database" of profiles held as a map
    private val names = Map(
      "fb.id.0-dummy" -> "Dummy",
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill"
    )

    private val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )

    // Generating random sleeping times
    val random = new Random()

    // API -> Simulating fetching from a database
    def fetchProfile(id: String): Future[Profile] = Future {

      // Fetching from DB
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    // API -> Simulating fetching from a database
    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bfID = friends(profile.id)
      Profile(bfID, names(bfID))
    }
  } // SocialNetwork Ended

  // client Application: We want Mark to poke Bill
  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck") // We have client application knows the ID

  // Option 1 [Nested Futures] [A little ugly way of doing Poking]
  mark.onComplete {
    case Success(markProfile) =>
      val bill = SocialNetwork.fetchBestFriend(markProfile)
      bill.onComplete {
        case Success(billProfile) => markProfile.poke(billProfile)
        case Failure(exception) => exception.printStackTrace()
      }
    case Failure(exception) => exception.printStackTrace()
  }

  // Thread.sleep(1000)


  // Option 2: Functional Composition of futures i.e. map, flatMap, filter
  val nameOnWall: Future[String] = mark.map[String](profile => profile.name) //<- From Future[Profile] -> Future[String]
  // Remember: In Map if original future has failed with an exception then the mapped future will fail with same exception


  val marksBestFriend: Future[Profile] = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))

  // Filtering future based on predicate which will return future of same type
  val zuksBestFriendRestricted: Future[Profile] = marksBestFriend.filter(profile => profile.name.startsWith("z"))

  // for - comprehension
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)

  } mark.poke(bill)

  /**
    fallbacks
   */

  // Option 1
  val aProfileNoMatterWhat: Future[Profile] = SocialNetwork.fetchProfile("unknown id").recover {
    case e: Throwable => Profile("fb.id.0-dummy", "Forever alone")
  }

  // Option 2
  val aFetchedProfileNoMatterWhat: Future[Profile] = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
  }

  // Option 3
  val fallbackResult: Future[Profile] = SocialNetwork.fetchProfile("unknown id").fallbackTo {
    SocialNetwork.fetchProfile("fb.id.0-dummy")
  } // In case both future fails, then exception from first future will be contained in the failure

  Thread.sleep(1000)
}
