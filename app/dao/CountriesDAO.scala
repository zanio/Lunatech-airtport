package dao

import model.{Country, Runway}
import org.slf4j.LoggerFactory
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.PostgresProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.runtime.Nothing$

class CountriesTable(tag: Tag) extends Table[Country](tag, "countries") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def code = column[String]("code")

  def name = column[String]("name")

  def continent = column[Option[String]]("continent")

  def wikipedia_link = column[Option[String]]("wikipedia_link")

  def keywords = column[Option[String]]("keywords")

  def * =
    (id, code, name, continent, wikipedia_link, keywords) <> (Country.tupled, Country.unapply)
}

class CountriesDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[PostgresProfile] {

  val logger = LoggerFactory.getLogger(classOf[CountriesDAO])

  val countries = TableQuery[CountriesTable]

  val airports = TableQuery[AirportsTable]

  val runways = TableQuery[RunwaysTable]

  def get(id: Int): Future[Option[Country]] = {
    db.run(countries.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[Country]] = {
    db.run(countries.result)
  }

  def listAirportsAndRunways(nameOrCode: String): Future[(String, Map[String, Seq[Runway]])] = {
    val emptyMap: Map[String, Seq[Runway]] = Map.empty
    val countriesQuery = nameOrCode match {
      case code if code.length == 2 => logger.info("Getting airports and runways giving the country code: {}", code)
        countries.filter(_.code.toLowerCase === code.toLowerCase())
      case name => logger.info("Getting airports and runways giving the country name: {}", name)
        countries.filter(_.name.toLowerCase like s"${name.toLowerCase()}%")
    }
    val query = (for {
      country <- countriesQuery
      airport <- airports if country.code === airport.iso_country
      runway <- runways if airport.id === runway.airport_ref
    } yield (country, airport, runway)).result map {
      case tuples if !tuples.isEmpty => tuples.groupBy(_._1.name).map {
        res =>
          (res._1, res._2.groupBy(_._2.name).map {
            result => (result._1, result._2.map(x => x._3))
          })
      }.head
      case tuples if tuples.isEmpty => (nameOrCode, emptyMap)
    }
    db.run(query)
  }

  def countriesWithHighestAndLowestAirports(): Future[Seq[(String, Int, Seq[String])]] = {
    logger.info("Getting reports")
    val query = (for {
      country <- countries
      airport <- airports if country.code === airport.iso_country
      runway <- runways if airport.id === runway.airport_ref
    } yield (country, airport, runway)).result map {
      case tuples => tuples.groupBy(_._1.name).map {
        res =>
          (res._1, res._2.length, res._2.map(_._3.surface.getOrElse("")))
      }.map {
        res => (res._1, res._2, res._3.filter(_ != "").distinct)
      }.toSeq.sortBy(-_._2).take(10)
    }
    val query2 = (for {
      country <- countries
      airport <- airports if country.code === airport.iso_country
      runway <- runways if airport.id === runway.airport_ref
    } yield (country, airport, runway)).result map {
      case tuples => tuples.groupBy(_._1.name).map {
        res =>
          (res._1, res._2.length, res._2.map(_._3.surface.getOrElse("")))
      }.map {
        res => (res._1, res._2, res._3.filter(_ != "").distinct)
      }.toSeq.sortBy(-_._2).takeRight(10)
    }
    val finalResult = for {
      result1 <- db.run(query)
      result2 <- db.run(query2)
    } yield result1 ++ result2
    finalResult
  }

}