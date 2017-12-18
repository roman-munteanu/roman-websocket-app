package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class UnsubscribeGames(override val $type: String = "unsubscribe_games") extends MessageWithType($type)

object UnsubscribeGames {
  implicit val unsubscribeGamesJsonFormat = Json.format[UnsubscribeGames]
}