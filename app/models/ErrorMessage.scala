package models

import play.api.libs.json.Json

/**
  * Created by romunteanu on 12/1/17.
  */
case class ErrorMessage(code: String) extends MessageWithType("error_message")

object ErrorMessage {
  implicit val errorMessageJsonFormat = Json.format[ErrorMessage]
}
