package utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

abstract class DatabaseFlatSpec extends AnyFlatSpec with GuiceOneAppPerSuite {

  override def fakeApplication(): Application = {
    val builder = overrideDependencies(new GuiceApplicationBuilder())
    builder.build()
  }

  def overrideDependencies(application: GuiceApplicationBuilder): GuiceApplicationBuilder = {
    application
  }

}
