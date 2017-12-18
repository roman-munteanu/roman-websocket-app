package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class GameList(games: Seq[Game]) extends MessageWithType("game_list")

object GameList {
  implicit val gameListJsonFormat = Json.format[GameList]
}