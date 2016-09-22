package yd.ak

import java.util.{Calendar, Date}

import akka.actor.{Actor, ActorLogging, Props}
import yd.ak.PollManager.SnapShot
import yd.poll.{Poll, PollVotes}

object PollActor {
  def props(poll: Poll) = Props(classOf[PollActor], poll)
}

class PollActor(poll: Poll) extends Actor with ActorLogging {
  import yd.ak.PollManager.Vote
  val votes = new PollVotes(poll)
  var snapShoted = 0L

  override def receive: Receive = {
    case Vote(p, option) if p == poll =>
      log.info(s"Voting for [${option.text}] in [${p.text}]")
      votes vote option.id
    case s @ SnapShot(p, _) if p == poll =>
      val now = new Date().getTime
      if (now - snapShoted > 2000) {
        sender() ! s.copy(snapshot = votes.snapshot)
        snapShoted = now
      }
  }
}
