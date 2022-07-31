package in.kamranali.book.programmingInScala.section29.recipeApp.db

import in.kamranali.book.programmingInScala.section29.recipeApp.db.traits.FoodCategories
import in.kamranali.book.programmingInScala.section29.recipeApp.entity.{Food, Recipe}

abstract class Database extends FoodCategories {

  // abstract stuff
  def allFoods: List[Food]
  def allRecipes: List[Recipe]

  // concrete stuff
  def foodNamed(name: String): Option[Food] = allFoods.find(_.name == name)
}
