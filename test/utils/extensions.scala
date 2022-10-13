package utils

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object extensions {

  implicit class RichFuture[T](future: Future[T]) {
    def awaitForResult: T = Await.result(future, Duration.Inf)
  }

}