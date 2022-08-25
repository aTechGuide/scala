package guide.atech.algorithms.cflt.functionlibrary.model

trait FunctionLibrary[F, A] {
  def register(functionSet: Set[F]): Unit
  def findMatches(argumentTypes: List[A]): List[A]
}
