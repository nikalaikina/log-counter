package com.github.nikalaikina.coral

import cats.effect.{ExitCode, IO, IOApp, Resource, ResourceIO}
import com.github.nikalaikina.coral.config.AppConfig
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import fs2.Stream

import java.io.{BufferedReader, InputStream, InputStreamReader}

object Boot extends IOApp {

  def input(stream: InputStream): Resource[IO, BufferedReader] = {
    Resource.make(IO.delay(new BufferedReader(new InputStreamReader(stream))))(r =>
      IO.delay(r.close())
    )
  }

  def stream(reader: BufferedReader): Stream[IO, String] = {
    Stream.repeatEval(IO.delay(reader.readLine()))
  }

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- IO.delay(ConfigSource.default.loadOrThrow[AppConfig])
      process <- IO.delay(Runtime.getRuntime.exec(config.processCommand))
      input <- input(process.getInputStream)
        .use(i => stream(i).evalMap(s => IO.delay(println(s))).compile.drain)
      _ <- IO.delay(println(input))
    } yield ExitCode.Success
  }
}
