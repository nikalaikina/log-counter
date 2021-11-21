package com.github.nikalaikina.coral.json
import com.github.nikalaikina.coral.domain.{EventType, Log, Word}
import io.circe.Decoder
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras._

object LogDecoder {
  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames

  implicit val eventTypeDecoder: Decoder[EventType] = Decoder[String].map(EventType)
  implicit val wordDecoder: Decoder[Word] = Decoder[String].map(Word)
  implicit val logDecoder: Decoder[Log] = deriveConfiguredDecoder
}
