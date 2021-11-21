package com.github.nikalaikina.coral.http
import cats.effect._
import com.github.nikalaikina.coral.WordCounter
import org.http4s._
import org.http4s.dsl.io._

class WordCountRoute[F[_]](val service: WordCounter[F]) {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "words-count" =>
      Ok(s"Hello.")
    case GET -> Root / "words-count" / word =>
      Ok(s"Hello, $word.")
  }
}
