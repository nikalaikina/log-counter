name := "log-counter"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.17.0"

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.2.9"

val http4sVersion = "0.23.6"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)
