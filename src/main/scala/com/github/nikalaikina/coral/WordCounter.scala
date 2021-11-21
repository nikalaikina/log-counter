package com.github.nikalaikina.coral

import cats.effect.Ref
import com.github.nikalaikina.coral.domain.{Log, WordsState}

class WordCounter[F[_]](windowSize: Int, state: Ref[F, WordsState]) {

  def processLog(log: Log): F[Unit] =
    state.update(_.addWord(windowSize, log.eventType, log.data))

  def getState: F[WordsState] =
    state.get
}
