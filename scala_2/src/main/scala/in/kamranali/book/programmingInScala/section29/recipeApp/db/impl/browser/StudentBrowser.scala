package in.kamranali.book.programmingInScala.section29.recipeApp.db.impl.browser

import in.kamranali.book.programmingInScala.section29.recipeApp.db.impl.database.StudentDatabase
import in.kamranali.book.programmingInScala.section29.recipeApp.db.{Browser, Database}

object StudentBrowser extends Browser {
  override val database: Database = StudentDatabase
}
