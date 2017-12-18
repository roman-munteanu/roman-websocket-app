package models

import akka.actor.ActorRef

/**
  * Created by romunteanu
  */
case class JoinRoom(username: String, out: ActorRef)
