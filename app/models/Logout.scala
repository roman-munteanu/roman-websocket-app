package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class Logout(override val $type: String = "logout") extends MessageWithType($type)

object Logout {
  implicit val logoutJsonFormat = Json.format[Logout]
}