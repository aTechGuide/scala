package guide.atech.algorithms.cflt.patternmatching

trait PatternMatching[T, R] {
  def isMatch(s: T, p: T): R
}
