package com.github.nikalaikina.coral.domain

import cats.implicits._
import com.github.nikalaikina.coral.domain.WordsState.WordCount

import scala.collection.immutable.Queue

case class WordsState(
    events: Map[EventType, WordCount] = Map.empty,
    queue: Queue[(EventType, Word)] = Queue.empty
) {

  private def get(eventType: EventType): WordCount = {
    events.getOrElse(eventType, WordCount())
  }

  def addWord(windowSize: Int, eventType: EventType, newWord: Word): WordsState = {
    if (queue.size < windowSize) {
      copy(
        events = events + (eventType -> get(eventType).addWord(newWord)),
        queue = queue.enqueue(eventType -> newWord)
      )
    } else {
      val ((typeToDelete, wordToDelete), newQueue) = queue.dequeue
      val newEvents = {
        val newType = get(typeToDelete).removeWord(wordToDelete)
        if (newType.isEmpty) {
          events - typeToDelete
        } else {
          events + (typeToDelete -> newType)
        }
      }
      copy(events = newEvents, queue = newQueue)
        .addWord(windowSize, eventType, newWord)
    }
  }
}

object WordsState {

  case class WordCount(
      map: Map[Word, Int] = Map.empty
  ) {

    def isEmpty: Boolean = map.isEmpty

    def addWord(word: Word): WordCount = {
      copy(map |+| Map(word -> 1))
    }

    def removeWord(word: Word): WordCount = {
      if (map.get(word).exists(_ > 1)) {
        copy(map |+| Map(word -> -1))
      } else {
        copy(map - word)
      }
    }
  }
}
