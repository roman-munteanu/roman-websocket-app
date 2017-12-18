package models

import play.api.libs.json.{JsValue, Json}

/**
  * Created by romunteanu
  */
trait MessageTypeConverter {

  implicit def jsonToModel(jsValue: JsValue): MessageWithType =
    (jsValue \ "$type").as[String] match {
      case "ping" => jsValue.as[Ping]
      case "login" => jsValue.as[Login]
      case "logout" => jsValue.as[Logout]
      case "subscribe_games" => jsValue.as[SubscribeGames]
      case "add_game" => jsValue.as[AddGame]
      case "update_game" => jsValue.as[UpdateGame]
      case "remove_game" => jsValue.as[RemoveGame]
      case "unsubscribe_games" => jsValue.as[UnsubscribeGames]
      case _ => ErrorMessage("bad_request")
    }

  implicit def modelToJson(msg: MessageWithType): JsValue =
    msg match {
      case m: Pong => Json.toJson(m)
      case m: ResponseMessage => Json.toJson(m)
      case m: GameList => Json.toJson(m)
      case m: AddGame => Json.toJson(m)
      case m: UpdateGame => Json.toJson(m)
      case m: RemoveGame => Json.toJson(m)
      case m: UpdateFailed => Json.toJson(m)
      case m: RemovalFailed => Json.toJson(m)
      case m: LoginFailed => Json.toJson(m)
      case m: LoginSuccessful => Json.toJson(m)
    }
}
