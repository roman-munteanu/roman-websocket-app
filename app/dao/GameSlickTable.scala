package dao

import models.Game
import slick.driver.PostgresDriver.api._

/**
  * Created by romunteanu
  */
class GameSlickTable(tag: Tag) extends Table[Game](tag, "games") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def participants = column[Int]("participants")

  def * = (id.?, name, participants) <> ((Game.apply _).tupled, Game.unapply)
}