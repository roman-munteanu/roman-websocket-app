package actors

import akka.actor.Actor.Receive
import akka.actor.{Props, Actor, ActorRef}
import models.{OutEvent, InEvent}
import play.api.Logger
import play.api.libs.json.JsValue

/**
  * Created by romunteanu
  */
class MyWebSocketActor(out: ActorRef) extends Actor {
  override def receive: Receive = {
    case msg: String =>
      Logger.info(s"MyWebSocketActor received message: $msg")
      out ! s"Received your message: $msg"
    case jsonMsg: JsValue =>
      Logger.info(s"MyWebSocketActor received json: $jsonMsg")
      out ! jsonMsg
    case event: InEvent =>
      Logger.info(s"MyWebSocketActor received event: $event")
      out ! OutEvent("OutEvent response")
    case x =>
      Logger.info(s"Received message with a non-detected type")
  }

  override def postStop() {
    Logger.info("Disconnected.")
  }
}

object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}