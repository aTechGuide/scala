package in.kamranali.book.programmingInScala.section29.recipeApp.entity.recipes

import in.kamranali.book.programmingInScala.section29.recipeApp.entity.{Food, Recipe}

object FrozenFood extends Food("FrozenFood")

object HeatItUp extends Recipe("Heat it Up", List(FrozenFood), "Microwave for 10 minutes")
