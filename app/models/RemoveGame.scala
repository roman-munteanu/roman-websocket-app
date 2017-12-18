package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class RemoveGame(override val $type: String = "remove_game", id: Long) extends MessageWithType($type)

object RemoveGame {
  implicit val RemoveGameJsonFormat = Json.format[RemoveGame]
}
