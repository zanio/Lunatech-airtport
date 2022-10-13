package util

import akka.stream.alpakka.slick.scaladsl.SlickSession

trait SlickSessionProvider {
  val session: SlickSession
}
