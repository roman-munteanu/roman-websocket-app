package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class UserAccount(id: Option[Long], username: String, password: String, userType: String, isSubscribed: Boolean)

object UserAccount {
  implicit val userAccountJsonFormat = Json.format[UserAccount]
}
