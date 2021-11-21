package com.github.nikalaikina.coral

import cats.effect.{ExitCode, IO, IOApp, Resource, ResourceIO}
import com.github.nikalaikina.coral.config.AppConfig
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import java.io.{BufferedReader, InputStream, InputStreamReader}

object Boot extends IOApp {

  def input(stream: InputStream): Resource[IO, BufferedReader] = {
    Resource.make(IO.delay(new BufferedReader(new InputStreamReader(stream))))(r =>
      IO.delay(r.close())
    )
  }

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- IO.delay(ConfigSource.default.loadOrThrow[AppConfig])
      process <- IO.delay(Runtime.getRuntime.exec(config.processCommand))
      input <- input(process.getInputStream).use(i => IO.delay(i.readLine()))
      _ <- IO.delay(println(input))
    } yield ExitCode.Success
  }
}
