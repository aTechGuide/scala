package in.kamranali.book.programmingInScala.section29.recipeApp.entity.recipes

import in.kamranali.book.programmingInScala.section29.recipeApp.entity.{Food, Recipe}

object Apple extends Food("Apple")
object Orange extends Food("Orange")
object Cream extends Food("Cream")

object FruitSalad extends Recipe("Fruit Salad", List(Apple, Orange, Cream), "Stir it all together")
