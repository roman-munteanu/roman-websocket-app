package controllers

import javax.inject._
import actors.{RoomActor, GameWebSocketActor, MyWebSocketActor}
import akka.actor.{Props, ActorSystem}
import akka.stream.Materializer
import dao.impl.{UserAccountDaoImpl, GameDaoImpl}
import models.{OutEvent, InEvent}
import play.api._
import play.api.libs.json.JsValue
import play.api.mvc.WebSocket.MessageFlowTransformer
import play.api.mvc._
import play.api.libs.streams._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (
    implicit actorSystem: ActorSystem,
    materializer: Materializer,
    userAccountDao: UserAccountDaoImpl,
    gameDao: GameDaoImpl) extends Controller {

  implicit val messageFlowTransformer = MessageFlowTransformer.jsonMessageFlowTransformer[InEvent, OutEvent]

  val roomActor = actorSystem.actorOf(Props[RoomActor], "RoomActor")

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => MyWebSocketActor.props(out))
  }

  def socketEvent = WebSocket.accept[InEvent, OutEvent] { request =>
    ActorFlow.actorRef(out => MyWebSocketActor.props(out))
  }

  def socketSport = WebSocket.accept[JsValue, JsValue] { request =>
    ActorFlow.actorRef(out => GameWebSocketActor.props(out, roomActor, userAccountDao, gameDao))
  }
}
