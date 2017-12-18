package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class UpdateGame(override val $type: String = "update_game", game: Game) extends MessageWithType($type)

object UpdateGame {
  implicit val updateGameJsonFormat = Json.format[UpdateGame]
}