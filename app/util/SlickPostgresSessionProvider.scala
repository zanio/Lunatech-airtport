package util

import akka.stream.alpakka.slick.scaladsl.SlickSession
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}

@Singleton
class SlickPostgresSessionProvider @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
    extends SlickSessionProvider {
  override val session: SlickSession = SlickSession.forConfig(dbConfigProvider.get[JdbcProfile])
}
