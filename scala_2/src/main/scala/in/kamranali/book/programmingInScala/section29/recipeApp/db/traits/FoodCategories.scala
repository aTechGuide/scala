package in.kamranali.book.programmingInScala.section29.recipeApp.db.traits

import in.kamranali.book.programmingInScala.section29.recipeApp.entity.Food

trait FoodCategories {

  case class FoodCategory(name: String, foods: List[Food])
  def allCategories: List[FoodCategory]
}
