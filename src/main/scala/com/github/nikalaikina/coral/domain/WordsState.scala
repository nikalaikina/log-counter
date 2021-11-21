package com.github.nikalaikina.coral.domain

import cats.implicits._
import com.github.nikalaikina.coral.domain.WordsState.WordCount

import scala.collection.immutable.Queue

case class WordsState(events: Map[EventType, WordCount] = Map.empty) {
  def addWord(windowSize: Int, eventType: EventType, word: Word): WordsState = {
    WordsState(
      events + (eventType -> events.getOrElse(eventType, WordCount()).addWord(windowSize, word))
    )
  }
}

object WordsState {

  case class WordCount(
      map: Map[Word, Int] = Map.empty,
      queue: Queue[Word] = Queue.empty
  ) {
    def addWord(windowSize: Int, newWord: Word): WordCount = {
      if (queue.size < windowSize) {
        copy(
          map = map |+| Map(newWord -> 1),
          queue = queue.enqueue(newWord)
        )
      } else {
        val (wordToDelete, newQueue) = queue.dequeue
        copy(
          map = map |+| Map(newWord -> 1, wordToDelete -> -1),
          queue = newQueue.enqueue(newWord)
        )
      }
    }
  }
}
