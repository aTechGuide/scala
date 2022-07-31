package in.kamranali.book.programmingInScala.section29.recipeApp.entity

class Recipe(val name: String, val ingredients: List[Food], val instructions: String) {
  override def toString: String = name
}
