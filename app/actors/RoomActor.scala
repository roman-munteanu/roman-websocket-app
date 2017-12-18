package actors

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive
import models.{MessageTypeConverter, BroadcastEvent, LeaveRoom, JoinRoom}
import play.api.Logger

/**
  * Created by romunteanu
  */
class RoomActor extends Actor with MessageTypeConverter {

  val userActorMap = scala.collection.mutable.Map[String, ActorRef]()

  override def receive: Receive = {
    case JoinRoom(username, out) =>
      Logger.info(s"Join: $username")
      userActorMap(username) = out
    case LeaveRoom(username) =>
      Logger.info(s"Leave: $username")
      userActorMap -= username
    case BroadcastEvent(event) => {
      val jsonEvent = modelToJson(event)
      Logger.info(s"Broadcast: ${jsonEvent.toString()}")
      userActorMap.foreach { entry =>
        entry._2 ! jsonEvent
      }
    }
  }
}
