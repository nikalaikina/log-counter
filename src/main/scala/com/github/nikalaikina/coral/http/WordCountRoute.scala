package com.github.nikalaikina.coral.http
import cats.Monad
import cats.data.Kleisli
import cats.implicits._
import com.github.nikalaikina.coral.WordCounter
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.Router

class WordCountRoute[F[_]: Monad](service: WordCounter[F]) extends Http4sDsl[F] {
  import com.github.nikalaikina.coral.json.WordsEncoder._
  import org.http4s.circe.CirceEntityCodec._

  private val routes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "words-count" =>
      service.getState.flatMap(Ok(_))
  }

  val httpApp: Kleisli[F, Request[F], Response[F]] = Router("/" -> routes).orNotFound
}
