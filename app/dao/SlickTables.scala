package dao

import slick.driver.PostgresDriver.api.TableQuery

/**
  * Created by romunteanu
  */
trait SlickTables {
  lazy val users = TableQuery[UserAccountSlickTable]
  lazy val games = TableQuery[GameSlickTable]
}
