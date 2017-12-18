package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class LoginSuccessful(override val $type: String = "login_successful", userType: String) extends MessageWithType($type)

object LoginSuccessful {
  implicit val loginSuccessfulJsonFormat = Json.format[LoginSuccessful]
}