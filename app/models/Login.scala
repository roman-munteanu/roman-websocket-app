package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class Login(username: String, password: String) extends MessageWithType("login")

object Login {
  implicit val loginJsonFormat = Json.format[Login]
}