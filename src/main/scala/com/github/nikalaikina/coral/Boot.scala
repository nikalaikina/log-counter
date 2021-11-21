package com.github.nikalaikina.coral

import cats.effect.{ExitCode, IO, IOApp}
import com.github.nikalaikina.coral.ProcessOutputStream.runProcess
import com.github.nikalaikina.coral.config.AppConfig
import io.circe.parser._
import pureconfig.ConfigSource
import pureconfig.generic.auto._

object Boot extends IOApp {
  import com.github.nikalaikina.coral.json.LogDecoder._

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- IO.delay(ConfigSource.default.loadOrThrow[AppConfig])
      _ <- runProcess(config.processCommand).use { output =>
        output
          .evalMap { s =>
            IO.delay(println(parse(s).map(logDecoder.decodeJson)))
          }
          .compile
          .drain
      }
    } yield ExitCode.Success
  }
}
