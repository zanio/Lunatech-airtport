package dao

import org.scalatest.matchers.should.Matchers
import util.Logger
import utils.DatabaseFlatSpec
import utils.extensions.RichFuture

/**
 * Project working on lunatech-airport
 * New File created by ani in  lunatech-airport @ 14/10/2022  09:55
 */
class CountriesDAOTest extends DatabaseFlatSpec with Matchers
  with Logger {
  private val countryTableDaoTest        = app.injector.instanceOf[CountriesDAO]

  behavior of "CountriesDAOTest"

  it should "listAirportsAndRunways" in {
    val result =  countryTableDaoTest.listAirportsAndRunways("ng").awaitForResult
    logger.info(s"The response -> ${result._1}" )
    result._1 should  equal("Nigeria")
    result._2 should  not be empty
  }

  it should "countriesWithHighestAndLowestAirports" in {
    val result =  countryTableDaoTest.countriesWithHighestAndLowestAirports().awaitForResult.head
    logger.info(s"countries with highest and lowest airports ${result._1}")

    result._1 should equal("United States")
    result._2 should equal(23745)
    result._3.size should equal(169)
  }

  it should "listAll" in {

  }

}
