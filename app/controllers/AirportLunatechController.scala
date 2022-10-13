package controllers

import dao.CountriesDAO

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class AirportLunatechController @Inject()(cc: ControllerComponents, countriesDAO: CountriesDAO) extends AbstractController(cc) with Logging {

  //query endpoint
  def query(nameOrCode: String) = Action.async { implicit request =>
    countriesDAO.listAirportsAndRunways(nameOrCode) map { result =>
      if(result._2.isEmpty)  Ok(views.html.noresult(result._1))
      else                   Ok(views.html.query(result))
    }
  }

  //reports endpoint
  def reports() = Action.async { implicit request =>
    countriesDAO.countriesWithHighestAndLowestAirports() map { result =>
      Ok(views.html.reports(result))
    }
  }
}
