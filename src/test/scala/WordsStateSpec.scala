import com.github.nikalaikina.coral.domain.WordsState.WordCount
import com.github.nikalaikina.coral.domain.{EventType, Word, WordsState}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class WordsStateSpec extends AnyFlatSpec with should.Matchers {

  val A: Word = Word("a")
  val Type1: EventType = EventType("type1")
  val Type2: EventType = EventType("type2")
  val Type3: EventType = EventType("type3")
  val Type4: EventType = EventType("type4")

  "WordsState" should "group one type" in {
    val windowSize = 3

    val logs = List(
      Type1 -> A,
      Type1 -> A,
      Type1 -> A,
      Type1 -> A
    )

    val result = logs.foldLeft(WordsState()) {
      case (state, (t, w)) => state.addWord(windowSize, t, w)
    }

    result.events shouldBe Map(
      Type1 -> WordCount(Map(A -> 3))
    )
  }

  "WordsState" should "group two types" in {
    val windowSize = 3

    val logs = List(
      Type1 -> A,
      Type1 -> A,
      Type1 -> A,

      Type2 -> A
    )

    val result = logs.foldLeft(WordsState()) {
      case (state, (t, w)) => state.addWord(windowSize, t, w)
    }

    result.events shouldBe Map(
      Type1 -> WordCount(Map(A -> 2)),
      Type2 -> WordCount(Map(A -> 1))
    )
  }

  "WordsState" should "group four types" in {
    val windowSize = 3

    val logs = List(
      Type1 -> A,
      Type2 -> A,
      Type3 -> A,
      Type4 -> A
    )

    val result = logs.foldLeft(WordsState()) {
      case (state, (t, w)) => state.addWord(windowSize, t, w)
    }

    result.events shouldBe Map(
      Type2 -> WordCount(Map(A -> 1)),
      Type3 -> WordCount(Map(A -> 1)),
      Type4 -> WordCount(Map(A -> 1))
    )
  }
}
