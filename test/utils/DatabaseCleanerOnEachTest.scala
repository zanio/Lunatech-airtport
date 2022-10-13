package utils

import java.io.File
import org.apache.commons.io.FileUtils
import org.scalatest.{BeforeAndAfterEach, Suite}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.{JdbcProfile, SQLActionBuilder}
import slick.jdbc.SQLiteProfile.api._
import util.{SlickPostgresSessionProvider, SlickSessionProvider}

import scala.util.Try
import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

trait DatabaseCleanerOnEachTest extends HasDatabaseConfigProvider[JdbcProfile] with BeforeAndAfterEach {
  this: Suite with DatabaseFlatSpec =>

  override lazy val dbConfigProvider: DatabaseConfigProvider =
    app.injector.instanceOf[DatabaseConfigProvider]

  val slickSessionProvider: SlickSessionProvider = app.injector.instanceOf[SlickPostgresSessionProvider]

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    clearDatabase()
  }

  override protected def afterEach(): Unit = {
    super.afterEach()
    deleteLocalFiles()
  }

  def clearDatabase(): Unit = {
    Try(truncateTables())
    Try(resetSequences())
  }

  def truncateTables(): Unit = {
    val tableToTruncate: SQLActionBuilder =
      sql"""SELECT tablename FROM pg_tables
           |WHERE  tableowner = 'admin'
           |AND schemaname = 'riskcontrol'
           |AND NOT tablename LIKE '%flyway%'
           |AND NOT tablename LIKE '%oauth%'""".stripMargin

    def truncate(name: String) =
      sql"""TRUNCATE TABLE #$name
           CASCADE;"""

    val runTruncate =
      for {
        tables <- tableToTruncate.as[String]
        t = tables.mkString("\"", "\", \"", "\"")
        _ <- truncate(t).as[Int]
      } yield ()

    Await.result(
      db.run(runTruncate)
        .recover{ case e: Exception =>
          println(s"ERROR - Failed to truncate tables: ${e.getMessage}")
        },
      Duration.Inf
    )
  }

  def resetSequences(): Unit = {
    val sequencesToReset: SQLActionBuilder =
      sql"""SELECT sequencename
           |FROM pg_sequences
           |WHERE sequenceowner='admin'
           |AND schemaname = 'riskcontrol'""".stripMargin

    def reset(name: String) =
      sqlu"""ALTER SEQUENCE #$name RESTART WITH 1;"""

    val runReset =
      for {
        sequences <- sequencesToReset.as[String]
        _ <- DBIO.sequence(sequences.map(s => reset(s"\"$s\"")))
      } yield ()

    Await.result(
      db.run(runReset)
        .recover{ case e: Exception =>
          println(s"ERROR - Failed to reset sequences: ${e.getMessage}")
        },
      Duration.Inf
    )
  }

  def deleteLocalFiles(): Unit = {
    val folder = app.configuration.get[String]("fs.folderName")
    Try(FileUtils.deleteDirectory(new File(folder)))
      .recover{ case e: Exception => println(s"cannot delete localFiles in ./test : $e") }
  }
}
