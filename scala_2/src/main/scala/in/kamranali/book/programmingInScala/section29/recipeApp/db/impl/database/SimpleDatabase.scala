package in.kamranali.book.programmingInScala.section29.recipeApp.db.impl.database

import in.kamranali.book.programmingInScala.section29.recipeApp.db.Database
import in.kamranali.book.programmingInScala.section29.recipeApp.entity.recipes.{Apple, Cream, FruitSalad, Orange}

object SimpleDatabase extends Database {

  // fields
  private var categories = List(FoodCategory("Fruits", List(Apple, Orange)), FoodCategory("misc", List(Cream)))

  // defining abstract methods
  override def allFoods = List(Apple, Orange, Cream)

  override def allRecipes = List(FruitSalad)

  override def allCategories: List[FoodCategory] = categories
}
