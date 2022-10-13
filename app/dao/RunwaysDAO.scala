package dao

import model.Runway
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.PostgresProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class RunwaysTable(tag: Tag) extends Table[Runway](tag, "runways") {

  def id = column[Int]("id"  , O.PrimaryKey,O.AutoInc)
  def airport_ref = column[Option[Int]]("airport_ref")
  def airport_ident = column[Option[String]]("airport_ident")
  def length_ft = column[Option[Int]]("length_ft")
  def width_ft = column[Option[Int]]("width_ft")
  def surface = column[Option[String]]("surface")
  def lighted = column[Option[Boolean]]("lighted")
  def closed = column[Option[Boolean]]("closed")
  def le_ident = column[Option[String]]("le_ident")
  def le_latitude_deg = column[Option[Float]]("le_latitude_deg")
  def le_longitude_deg = column[Option[Float]]("le_longitude_deg")
  def le_elevation_ft = column[Option[Int]]("le_elevation_ft")
  def le_heading_degt = column[Option[Float]]("le_heading_degt")
  def le_displaced_threshold_ft = column[Option[Int]]("le_displaced_threshold_ft")
  def he_ident = column[Option[String]]("he_ident")
  def he_latitude_deg = column[Option[Float]]("he_latitude_deg")
  def he_longitude_deg = column[Option[Float]]("he_longitude_deg")
  def he_elevation_ft = column[Option[Int]]("he_elevation_ft")
  def he_heading_degt = column[Option[Float]]("he_heading_degt")
  def he_displaced_threshold_ft = column[Option[Int]]("he_displaced_threshold_ft")

  override def * =
    (id, airport_ref, airport_ident, length_ft, width_ft, surface, lighted, closed, le_ident, le_latitude_deg, le_longitude_deg, le_elevation_ft, le_heading_degt, le_displaced_threshold_ft, he_ident, he_latitude_deg, he_longitude_deg, he_elevation_ft, he_heading_degt, he_displaced_threshold_ft) <> (Runway.tupled, Runway.unapply)
}

class RunwaysDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[PostgresProfile] {

  val runways = TableQuery[RunwaysTable]

  def listAll: Future[Seq[Runway]] = {
    db.run(runways.result)
  }
}