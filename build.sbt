name := """play-testing"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"



libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "de.leanovate.play-mockws" %% "play-mockws" % "2.4.0" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator