package controllers

import javax.inject._

import dao.impl.UserAccountDaoImpl
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class UserAccountController @Inject()(userAccountDao: UserAccountDaoImpl)(implicit exec: ExecutionContext) extends Controller {

  def all = Action.async {
    userAccountDao.findAll().map(users => Ok(Json.toJson(users)))
  }

}
