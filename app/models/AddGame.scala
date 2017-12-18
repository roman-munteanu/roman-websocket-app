package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class AddGame(override val $type: String = "add_game", afterId: Long, game: Game) extends MessageWithType($type)

object AddGame {
  implicit val addGameJsonFormat = Json.format[AddGame]
}
