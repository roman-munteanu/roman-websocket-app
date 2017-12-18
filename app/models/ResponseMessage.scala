package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class ResponseMessage(response: String) extends MessageWithType("response")

object ResponseMessage {
  implicit val responseMessageJsonFormat = Json.format[ResponseMessage]
}
