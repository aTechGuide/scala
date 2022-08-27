package guide.atech.algorithms.cflt.ide_autocomplete

class ClassNameIndexSpec extends munit.FunSuite {
  test("prefix is matched correctly") {


    val cni = ClassNameIndex()
    val classNames = List("Container", "Panel", "AutoPanel", "RidePrinter", "ResumePanel", "RegularContainer", "RegularContainerBall")

    classNames.foreach { cls => cni.add(cls)}

//    println(cni.matchPrefix("Re"))
    assertEquals(cni.matchPrefix("R"), List("ResumePanel", "RegularContainer", "RegularContainerBall", "RidePrinter"))
    assertEquals(cni.matchPrefix("Re"), List("ResumePanel", "RegularContainer", "RegularContainerBall"))
    assertEquals(cni.matchPrefix("RP"), List("ResumePanel", "RidePrinter"))
    assertEquals(cni.matchPrefix("RPr"), List("RidePrinter"))
    assertEquals(cni.matchPrefix("RCB"), List("RegularContainerBall"))
    assertEquals(cni.matchPrefix("RCoBa"), List("RegularContainerBall"))
  }
}
