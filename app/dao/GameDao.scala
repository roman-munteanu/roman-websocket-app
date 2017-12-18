package dao

import models.Game
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
  * Created by romunteanu
  */
trait GameDao extends HasDatabaseConfigProvider[JdbcProfile] with SlickTables {
  import dbConfig.driver.api._
  import play.api.libs.concurrent.Execution.Implicits.defaultContext

  def queryById(id: Long) = games.filter(_.id === id)

  def count(): Future[Int] = db.run(games.size.result)

  def findAll(): Future[Seq[Game]] = db.run(games.result)

  def findOne(id: Long): Future[Option[Game]] = db.run(queryById(id).result.headOption)

  def delete(id: Long): Future[Int] = db.run(queryById(id).delete)

  def update(id: Long, entity: Game): Future[Int] = db.run(queryById(id).update(entity))

  def add(entity: Game): Future[Int] = db.run(games += entity)

  def findByName(name: String): Future[Option[Game]] =
    db.run(games.filter(_.name === name).result.headOption)

  def insertAfter(afterId: Long, entity: Game): Future[Any] = {
    val theId = if (afterId > 0) afterId else 0
    val q = games.filter(_.id > theId)
    for {
      afterRows <- db.run(q.result)
      deleteResult <- db.run(q.delete)
      bulkInsertResult <- db.run(games ++= (entity +: afterRows))
    } yield bulkInsertResult
  }

}