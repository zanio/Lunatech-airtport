package dao

import org.scalatest.matchers.should.Matchers
import util.Logger
import utils.{DatabaseCleanerOnEachTest, DatabaseFlatSpec}
import utils.extensions.RichFuture
/**
 * Project working on lunatech-airport
 * New File created by ani in  lunatech-airport @ 13/10/2022  13:07
 */
class AirportsDAOTest extends DatabaseFlatSpec
  with Matchers
  with Logger {
  private val airportsTableDaoTest        = app.injector.instanceOf[AirportsDAO]


  it should "listAll" in {
   val airports = airportsTableDaoTest.listAll.awaitForResult
    logger.info(s"count number of airports fetched is as follows -> ${airports.size}")
    airports should not be empty
  }

}
