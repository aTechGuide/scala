package guide.atech.algorithms.router

import guide.atech.algorithms.team.router.BasicStringRouter
import guide.atech.algorithms.team.router.model.BasicTask
class BasicStringRouterSpec extends munit.FunSuite {

  test("Should be able to route message to a printer") {
    val routeMap = Map("1" -> BasicTask())
    val stringRouter = new BasicStringRouter(routeMap)
    stringRouter.router("1")
  }

}
