package in.kamranali.book.programmingInScala.section29.recipeApp.db

import in.kamranali.book.programmingInScala.section29.recipeApp.entity.{Food, Recipe}

abstract class Database {

  // Data structures
  case class FoodCategory(name: String, foods: List[Food])

  // abstract stuff
  def allFoods: List[Food]
  def allRecipes: List[Recipe]
  def allCategories: List[FoodCategory]

  // concrete stuff
  def foodNamed(name: String): Option[Food] = allFoods.find(_.name == name)
}
