package com.github.nikalaikina.coral

import cats.effect._
import com.github.nikalaikina.coral.ProcessOutputStream.runProcess
import com.github.nikalaikina.coral.config.AppConfig
import com.github.nikalaikina.coral.domain.WordsState
import com.github.nikalaikina.coral.http.WordCountRoute
import org.http4s.blaze.server.BlazeServerBuilder
import pureconfig.ConfigSource
import pureconfig.generic.auto._

object Boot extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val resource = for {
      config <- Resource.eval(loadConfig)
      logParser <- Resource.eval(LogParser.make[IO])
      ref <- Resource.eval(Ref.of[IO, WordsState](WordsState()))
      wordCounter = new WordCounter(config.windowSize, ref)
      output <- runProcess(config.processCommand)
      _ <- Resource.eval(
        logParser.parseLogs(output).evalMap(wordCounter.processLog).compile.drain.start
      )
      httpApp = new WordCountRoute(wordCounter).httpApp
      _ <- BlazeServerBuilder[IO].bindHttp().withHttpApp(httpApp).resource
    } yield ()

    resource.use(_ => IO.never).as(ExitCode.Success)
  }

  private def loadConfig: IO[AppConfig] =
    IO.delay(ConfigSource.default.loadOrThrow[AppConfig])
}
