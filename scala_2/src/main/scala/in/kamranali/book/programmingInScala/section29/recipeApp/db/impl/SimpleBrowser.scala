package in.kamranali.book.programmingInScala.section29.recipeApp.db.impl

import in.kamranali.book.programmingInScala.section29.recipeApp.db.{Browser, Database}

object SimpleBrowser extends Browser {
  override val database: Database = SimpleDatabase
}
