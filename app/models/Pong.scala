package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class Pong(seq: Int) extends MessageWithType("pong")

object Pong {
  implicit val pongJsonFormat = Json.format[Pong]
}