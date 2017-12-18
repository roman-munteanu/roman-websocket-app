package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class InEvent(request: String)

object InEvent {
  implicit val inEventFormat = Json.format[InEvent]
}
