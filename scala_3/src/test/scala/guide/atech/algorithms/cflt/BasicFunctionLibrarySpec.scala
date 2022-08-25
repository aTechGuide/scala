package guide.atech.algorithms.cflt

import guide.atech.algorithms.cflt.functionlibrary.BasicFunctionLibrary
import guide.atech.algorithms.cflt.functionlibrary.model.MyFunction

class BasicFunctionLibrarySpec extends munit.FunSuite {

  test("Correct functions are returned") {
    val funcLibrary = new BasicFunctionLibrary()

    funcLibrary.register(Set(
      MyFunction("FuncA", Array("String", "Int", "Int"), false),
      MyFunction("FuncB", Array("String","Int"), true),
      MyFunction("FuncC", Array("Int"), true),
      MyFunction("FuncD", Array("Int", "Int"), true),
      MyFunction("FuncE", Array("Int", "Int", "Int"), false),
      MyFunction("FuncF", Array("String"), false),
      MyFunction("FuncG", Array("Int"), false)
    ))

    // pass
    assertEquals(funcLibrary.findMatches(List("String")), List("FuncF"))
    assertEquals(funcLibrary.findMatches(List("Int")), List("FuncC", "FuncG"))
    assertEquals(funcLibrary.findMatches(List("Int", "Int", "Int", "Int")), List("FuncC", "FuncD"))
    assertEquals(funcLibrary.findMatches(List("String", "Int", "Int", "Int")), List("FuncB"))

    // fail
    assertEquals(funcLibrary.findMatches(List("Int", "Int", "Int")), List("FuncC", "FuncD", "FuncE"))
    assertEquals(funcLibrary.findMatches(List("String", "Int", "Int")), List("FuncB", "FuncA"))

  }

}
