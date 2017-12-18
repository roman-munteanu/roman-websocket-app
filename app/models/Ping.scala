package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class Ping(seq: Int) extends MessageWithType("ping")

object Ping {
  implicit val pingJsonFormat = Json.format[Ping]
}
