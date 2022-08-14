package guide.atech.designpattern.singleton

class AppContext private(val env: String)

object AppContext {

  @volatile
  var instance: Option[AppContext] = None

  def getOrCreate(env: String): AppContext = {
    if (instance.nonEmpty) {
      println(s"[Warning] AppContext is already initialized with env = ${instance.head.env}")
      instance.head
    }
    else {
      this synchronized {
        if (instance.nonEmpty) instance.head
        else {
          instance = Some(new AppContext(env))
          instance.head
        }
      }
    }
  }
}