package dao

import model.Airport
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.PostgresProfile

import javax.inject.Inject
import scala.collection.Seq
import scala.concurrent.{ExecutionContext, Future}

/**
 * Project working on lunatech-airport
 * New File created by ani in  lunatech-airport @ 13/10/2022  12:14
 */
class AirportsTable(tag: Tag) extends Table[Airport](tag, "airports") {

  def id = column[Int]("id"  , O.PrimaryKey,O.AutoInc)
  def ident = column[Option[String]]("ident")
  def `type` = column[Option[String]]("type")
  def name = column[String]("name")
  def latitude_deg = column[Option[Float]]("latitude_deg")
  def longitude_deg = column[Option[Float]]("longitude_deg")
  def elevation_ft = column[Option[Int]]("elevation_ft")
  def continent = column[Option[String]]("continent")
  def iso_country = column[Option[String]]("iso_country")
  def iso_region = column[Option[String]]("iso_region")
  def municipality = column[Option[String]]("municipality")
  def scheduled_service = column[Option[String]]("scheduled_service")
  def gps_code = column[Option[String]]("gps_code")
  def iata_code = column[Option[String]]("iata_code")
  def local_code = column[Option[String]]("local_code")
  def home_link = column[Option[String]]("home_link")
  def wikipedia_link = column[Option[String]]("wikipedia_link")
  def keywords = column[Option[String]]("keywords")


  def * =
    (id, ident, `type`, name, latitude_deg, longitude_deg, elevation_ft, continent, iso_country, iso_region, municipality, scheduled_service,
      gps_code, iata_code, local_code, home_link, wikipedia_link, keywords) <> (Airport.tupled, Airport.unapply)
}

class AirportsDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[PostgresProfile] {

  val airports = TableQuery[AirportsTable]

  def listAll: Future[Seq[Airport]] = {
    db.run(airports.result)
  }
}
