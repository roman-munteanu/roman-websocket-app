package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class UpdateFailed(override val $type: String = "update_failed", id: Long) extends MessageWithType($type)

object UpdateFailed {
  implicit val updateFailedJsonFormat = Json.format[UpdateFailed]
}