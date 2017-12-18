package dao

import models.UserAccount
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
  * Created by romunteanu
  */
trait UserAccountDao extends HasDatabaseConfigProvider[JdbcProfile] with SlickTables {
  import dbConfig.driver.api._

  def queryById(id: Long) = users.filter(_.id === id)

  def count(): Future[Int] = db.run(users.size.result)

  def findAll(): Future[Seq[UserAccount]] = db.run(users.result)

  def findOne(id: Long): Future[Option[UserAccount]] = db.run(queryById(id).result.headOption)

  def findByUsernameAndPassword(username: String, password: String): Future[Option[UserAccount]] =
    db.run(users.filter(u => u.username === username && u.password === password).result.headOption)
}
