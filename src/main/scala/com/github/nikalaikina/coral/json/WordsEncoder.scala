package com.github.nikalaikina.coral.json

import com.github.nikalaikina.coral.domain.WordsState.WordCount
import com.github.nikalaikina.coral.domain.{EventType, Word, WordsState}
import io.circe.{Encoder, KeyEncoder}

object WordsEncoder {

  implicit val eventTypeEncoder: KeyEncoder[EventType] = KeyEncoder.instance(_.value)
  implicit val wordEncoder: KeyEncoder[Word] = KeyEncoder.instance(_.value)

  implicit val WordCountEncoder: Encoder[WordCount] = Encoder[Map[Word, Int]].contramap(_.map)
  implicit val WordsStateEncoder: Encoder[WordsState] = Encoder[Map[EventType, WordCount]].contramap(_.events)
}
