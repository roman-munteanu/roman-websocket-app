package dao.impl

import javax.inject.{Inject, Singleton}

import dao.GameDao
import play.api.db.slick.DatabaseConfigProvider

/**
  * Created by romunteanu
  */
@Singleton
class GameDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends GameDao
