package yd.ak

import akka.actor.{Actor, ActorLogging, ActorRef, IndirectActorProducer, Props}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import yd.config.AppContextAware
import yd.poll.{Poll, PollOption}

object PollManager {
  case class CreatePoll(poll: Poll)
  case class Vote(poll: Poll, pollOption: PollOption)
  case class SnapShot(poll: Poll, snapshot: Map[PollOption, Int] = null)
}

class PollManager extends Actor with AppContextAware with ActorLogging {
  import yd.ak.PollManager._

  var polls: Map[Poll, ActorRef] = Map.empty
  lazy val store = context.actorOf(Props(classOf[PollVoteStoreFactory], appContext), "store")

  override def receive: Receive = {
    case CreatePoll(poll) if !polls.contains(poll) =>
      polls += poll -> context.actorOf(PollActor.props(poll), s"poll-${poll.id}")
      self ! SnapShot(poll) // take initial snapshot
    case v: Vote =>
      for (poll <- polls.values) { poll ! v }
    case s: SnapShot => // make PollActor reply result to the snapshot actor
      for (poll <- polls.values) { poll.tell(s, store) }
  }
}
