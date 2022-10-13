/**
 * Project working on lunatech-airport
 * New File created by ani in  lunatech-airport @ 13/10/2022  10:19
 */
import sbt._


object Dependencies {
  val slickPgVersion = "0.20.2"
  val databaseDependencies = Seq(
    "org.playframework.anorm" %% "anorm"              % "2.6.10", // Required for Silhouette
    "org.postgresql"          % "postgresql"          % "42.3.3",
    "com.typesafe.play"       %% "play-slick"         % "5.0.0",
    "com.github.tminglei"     %% "slick-pg"           % slickPgVersion,
    "com.github.tminglei"     %% "slick-pg_play-json" % slickPgVersion
  )

  val alpakkaVersion = "3.0.4"
  val alpakka = Seq(
    "com.lightbend.akka" %% "akka-stream-alpakka-slick" % alpakkaVersion,
  )
}
