package models

import play.api.libs.json.Json

/**
  * Created by romunteanu
  */
case class RemovalFailed(override val $type: String = "removal_failed", id: Long) extends MessageWithType($type)

object RemovalFailed {
  implicit val removalFailedJsonFormat = Json.format[RemovalFailed]
}
