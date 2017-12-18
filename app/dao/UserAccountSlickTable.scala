package dao

import models.UserAccount
import slick.driver.PostgresDriver.api._

/**
  * Created by romunteanu
  */
class UserAccountSlickTable(tag: Tag) extends Table[UserAccount](tag, "user_account") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username")
  def password = column[String]("password")
  def userType = column[String]("user_type")
  def isSubscribed = column[Boolean]("is_subscribed")

  def * = (id.?, username, password, userType, isSubscribed) <> ((UserAccount.apply _).tupled, UserAccount.unapply)

  def uniqueIdx = index("user_account_unique_idx", username, unique = true)
}
