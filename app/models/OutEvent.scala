package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class OutEvent(response: String)

object OutEvent {
  implicit val inEventFormat = Json.format[OutEvent]
}
