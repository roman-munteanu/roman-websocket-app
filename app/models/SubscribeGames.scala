package models

import play.api.libs.json.Json

/**
  * Created by romunteanu on 12/4/17.
  */
case class SubscribeGames(override val $type: String = "subscribe_games") extends MessageWithType($type)

object SubscribeGames {
  implicit val subscribeGamesJsonFormat = Json.format[SubscribeGames]
}
