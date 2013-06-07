import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "com.equalexperts.conference"
  val buildScalaVersion = "2.10.1"
  val buildVersion      = "0.1"

  val buildSettings = Defaults.defaultSettings ++
                      Seq (organization  := buildOrganization,
                           scalaVersion  := buildScalaVersion,
                           version       := buildVersion,
                           scalacOptions ++= Seq("-deprecation", "-language:all", "-unchecked", "-feature"))
}

object Dependencies {

  val extraResolvers = Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                           "Sonatype" at "http://oss.sonatype.org/content/repositories/snapshots/")

  // //////////////////////////////////////////////////////////////////////////////////////////
  // Compile Libraries

  val jodaTime = "joda-time" % "joda-time" % "1.6.2" % "compile"
  val utilities = Seq(jodaTime)

  val scalaz = "org.scalaz" %% "scalaz-core" % "7.0.0" % "compile"
  val functional = Seq(scalaz)

  // //////////////////////////////////////////////////////////////////////////////////////////
  // Testing Libraries

  val specs2 = "org.specs2" %% "specs2" % "1.14" % "test"
  val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.10.1" % "test"
  val mockito = "org.mockito" % "mockito-core" % "1.9.5" % "test"
  val testing = Seq(specs2, scalaCheck, mockito)

  // Dependency groups
  val coreDeps = utilities ++ functional ++ testing
}

object EEConBuild extends Build {
  import Dependencies._
  import BuildSettings._

  lazy val eeconProject = Project("eecon", file ("."),
           settings = buildSettings ++ Seq(resolvers ++= extraResolvers, libraryDependencies ++= coreDeps))
}
