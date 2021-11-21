package com.github.nikalaikina.coral

import cats.effect.Sync
import com.github.nikalaikina.coral.domain.Log
import fs2.Stream
import io.circe.parser._
import org.typelevel.log4cats.Logger
import cats.implicits._
import org.typelevel.log4cats.slf4j.Slf4jLogger

class LogParser[F[_]](logger: Logger[F]) {
  import com.github.nikalaikina.coral.json.LogDecoder._

  def parseLogs(output: Stream[F, String]): Stream[F, Log] = {
    output.flatMap { line =>
      parse(line).flatMap(logDecoder.decodeJson) match {
        case Left(error) =>
          Stream.eval(logger.warn(s"Error while output line($line): $error")) *> Stream.empty
        case Right(value) =>
          Stream.emit(value)
      }
    }
  }
}

object LogParser {

  def make[F[_]: Sync]: F[LogParser[F]] = {
    Slf4jLogger.create.map(new LogParser(_))
  }
}
