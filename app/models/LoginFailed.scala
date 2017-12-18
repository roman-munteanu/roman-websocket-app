package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class LoginFailed(override val $type: String = "login_failed") extends MessageWithType($type)

object LoginFailed {
  implicit val loginFailedJsonFormat = Json.format[LoginFailed]
}