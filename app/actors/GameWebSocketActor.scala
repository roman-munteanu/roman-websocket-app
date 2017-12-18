package actors

import akka.actor._
import dao.{UserAccountDao, GameDao}
import models._
import play.api.Logger
import play.api.libs.json.JsValue

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by romunteanu
  */
class GameWebSocketActor(out: ActorRef, roomActor: ActorRef, userAccountDao: UserAccountDao, gameDao: GameDao) extends Actor with MessageTypeConverter {

  import play.api.libs.concurrent.Execution.Implicits.defaultContext

  val atMost = 5 seconds

  private var loggedInUser: Option[UserAccount] = None

  override def receive: Receive = {
    case msg: JsValue =>
      Logger.info(s"GameWebSocketActor received msg: $msg")
      val response = processRequest(msg)
      Logger.info(s"Response: $response")
      out ! response
    case x =>
      Logger.info(s"Unsupported message type")
  }

  override def postStop() {
    Logger.info("Disconnected.")
  }

  private def processRequest(msg: MessageWithType): JsValue = {
    msg match {

      case m: Ping =>
        Pong(1)

      case m: Login =>
        val maybeUser: Option[UserAccount] = Await.result(userAccountDao.findByUsernameAndPassword(m.username, m.password), atMost)
        maybeUser match {
          case Some(user) =>
            loggedInUser = maybeUser
            LoginSuccessful(userType = user.userType)
          case None =>
            LoginFailed()
        }

      case m: Logout => {
        unsubscribe()
        loggedInUser = None
        ResponseMessage("logged_out")
      }

      case m: SubscribeGames if isAuthenticated() => {
        subscribe()
        Await.result(gameDao.findAll().map(t => GameList(t)), atMost)
      }

      case m: UnsubscribeGames if isAuthenticated() => {
        unsubscribe()
        ResponseMessage("unsubscribed")
      }

      case m: AddGame if isAuthorized() =>
        val futureGame = for {
          multipleInsertResult <- gameDao.insertAfter(m.afterId, m.game)
          t <- gameDao.findByName(m.game.name)
        } yield t

        val futureResult: Future[MessageWithType] = futureGame.map {
          case Some(tbl) =>
            val event = m.copy($type = "game_added", game = tbl)
            broadcastEvent(event)
            event
          case None =>
            AddGame("add_failed", 0, null)
        }
        awaitFutureResult(futureResult)

      case m: UpdateGame if isAuthorized() =>
        val futureResult: Future[MessageWithType] = gameDao.update(m.game.id.getOrElse(0), m.game).map {
          case updateResult if updateResult > 0 =>
            val event = m.copy($type = "game_updated")
            broadcastEvent(event)
            event
          case _ =>
            UpdateFailed(id = m.game.id.get)
        }
        awaitFutureResult(futureResult)

      case m: RemoveGame if isAuthorized() =>
        Option(m.id) match {
          case Some(id) =>
            val futureResult: Future[MessageWithType] = gameDao.delete(m.id).map {
              case removeResult if removeResult > 0 =>
                val event = m.copy($type = "game_removed")
                broadcastEvent(event)
                event
              case _ =>
                RemovalFailed(id = m.id)
            }
            awaitFutureResult(futureResult)
          case None =>
            RemovalFailed(id = m.id)
        }

      case _ =>
        ResponseMessage("not_authorized")
    }
  }

  private def isAuthenticated(): Boolean =
    loggedInUser.isDefined

  private def isAuthorized(): Boolean =
    loggedInUser.isDefined && loggedInUser.get.userType == "admin"

  private def awaitFutureResult(futureResult: Future[MessageWithType]): JsValue =
    Await.result(futureResult.map(res => modelToJson(res)), atMost)


  private def subscribe(): Unit = {
    roomActor ! JoinRoom(loggedInUser.get.username, out)
  }

  private def unsubscribe(): Unit = {
    roomActor ! LeaveRoom(loggedInUser.get.username)
  }

  private def broadcastEvent(event: MessageWithType): Unit = {
    roomActor ! BroadcastEvent(event)
  }
}

object GameWebSocketActor {
  def props(out: ActorRef, roomActor: ActorRef, userAccountDao: UserAccountDao, gameDao: GameDao) =
    Props(new GameWebSocketActor(out, roomActor, userAccountDao, gameDao))
}