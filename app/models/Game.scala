package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class Game(id: Option[Long], name: String, participants: Int)

object Game {
  implicit val gameJsonFormat = Json.format[Game]
}
