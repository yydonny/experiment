package yd.poll

import java.util.concurrent.atomic.AtomicInteger

import scala.beans.BeanProperty
import scala.collection.JavaConversions._

object polls {
  val poll1 = Poll(1, "favorite fruit",
      PollOption(1, "apple") ::
      PollOption(2, "orange") ::
      PollOption(3, "kiwi") ::
      PollOption(4, "watermelon") ::
      PollOption(5, "avocado") :: Nil)

  val poll2 = Poll(2, "Noble houses of the Seven Kingdoms",
      PollOption(1, "Stark of the North") ::
      PollOption(2, "Arryn of the Vale") ::
      PollOption(3, "Frey of the Riverland") ::
      PollOption(4, "Greyjoy of the Iron Islands") ::
      PollOption(5, "Lannister of the Westerlands and the Crownlands") ::
      PollOption(6, "Tyrell of the Reach") :: Nil)
}

case class Poll(@BeanProperty id: Int,
                @BeanProperty text: String,
                options: Seq[PollOption]) extends Equals {

  def option(id: Int) = options.find(_.id == id)
  def optionList: java.util.List[PollOption] = options

  override def equals(obj: Any): Boolean = obj match {
    case a: AnyRef if this eq a => true
    case p: Poll => p.canEqual(this) && id == p.id
    case _ => false
  }
}

case class PollOption(@BeanProperty id: Int,
                      @BeanProperty text: String)

class PollVotes(poll: Poll) {
  val options = poll.options
    .map(option => option.id -> new AtomicInteger)
    .foldLeft(Map.empty:Map[Int, AtomicInteger])(_ + _)

  def vote(id: Int): PollVotes = {
    for (vote <- options.get(id)) { vote.incrementAndGet() }
    this
  }

  def snapshot: Map[PollOption, Int] = options
    .map({ case (id, vote) => (poll.option(id), vote) })
    .filter(_._1.nonEmpty)
    .map({ case (k, v) => (k.get, v.get) })
    .foldLeft(Map.empty:Map[PollOption, Int])(_ + _)
}

