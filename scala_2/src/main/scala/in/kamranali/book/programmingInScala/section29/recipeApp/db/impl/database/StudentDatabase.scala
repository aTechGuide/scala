package in.kamranali.book.programmingInScala.section29.recipeApp.db.impl.database

import in.kamranali.book.programmingInScala.section29.recipeApp.db.Database
import in.kamranali.book.programmingInScala.section29.recipeApp.entity.recipes.{FrozenFood, FruitSalad}

object StudentDatabase extends Database {

  // fields
  private var categories = List(FoodCategory("Edible", List(FrozenFood)))

  // defining abstract methods
  override def allFoods = List(FrozenFood)

  override def allRecipes = List(FruitSalad)

  override def allCategories: List[FoodCategory] = categories
}
