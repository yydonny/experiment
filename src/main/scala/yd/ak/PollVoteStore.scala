package yd.ak

import akka.actor.{Actor, ActorLogging, IndirectActorProducer}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import yd.ak.PollManager.SnapShot
import yd.service.PollVoteCache

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PollVoteStore @Autowired()(cache: PollVoteCache) extends Actor with ActorLogging {
  override def receive: Receive = {
    case SnapShot(poll, snapshot) =>
      log.info(s" >>>>>> Snapshoting for [${poll.text}] ")
      cache.put(poll, snapshot)
  }
}

class PollVoteStoreFactory(context: ApplicationContext) extends IndirectActorProducer {
  override def actorClass: Class[_ <: Actor] = classOf[PollVoteStore]
  override def produce(): Actor = context.getBean(classOf[PollVoteStore])
}
