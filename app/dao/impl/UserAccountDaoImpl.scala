package dao.impl

import javax.inject.{Inject, Singleton}

import dao.UserAccountDao
import play.api.db.slick.DatabaseConfigProvider

/**
  * Created by romunteanu
  */
@Singleton
class UserAccountDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends UserAccountDao
