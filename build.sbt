name := "log-counter"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.17.0"

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.2.9"

libraryDependencies += "co.fs2" %% "fs2-core" % "3.2.0"

val http4sVersion = "0.23.6"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion
)

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-generic-extras" % circeVersion
)

val logVersion = "2.1.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "log4cats-core" % logVersion,
  "org.typelevel" %% "log4cats-slf4j" % logVersion
)

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.32"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
