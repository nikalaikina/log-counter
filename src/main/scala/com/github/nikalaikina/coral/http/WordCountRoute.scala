package com.github.nikalaikina.coral.http
import cats.data.Kleisli
import cats.effect._
import com.github.nikalaikina.coral.WordCounter
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.Router

class WordCountRoute[F[_]](service: WordCounter[F]) {
  private val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "words-count" =>
      Ok(s"Hello.")
    case GET -> Root / "words-count" / word =>
      Ok(s"Hello, $word.")
  }

  val httpApp: Kleisli[IO, Request[IO], Response[IO]] = Router("/" -> routes).orNotFound
}
