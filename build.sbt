ThisBuild / scalaVersion := "2.13.9"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """lunatech-airport""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )

PlayKeys.devSettings := Seq("play.server.http.port" -> "9001")

