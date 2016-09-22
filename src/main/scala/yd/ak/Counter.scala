package yd.ak

import akka.actor.{Actor, ActorLogging, IndirectActorProducer, Props}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import yd.service.CountingService

class CounterFactory (context: ApplicationContext, count: Int = 12) extends IndirectActorProducer {
  override def actorClass: Class[_ <: Actor] = classOf[Counter]
  override def produce(): Actor = {
    val counter = context.getBean(classOf[Counter])
    counter.count = count
    counter
  }
}

object Counter {
  case object Tick
  case object Get
}

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class Counter @Autowired() (countingService: CountingService) extends Actor with ActorLogging {
  import Counter._
  var count = 0
  def receive = {
    case Tick => {
      log.warning("Tick received!")
      count = countingService.increment(count)
    }
    case Get  => sender ! count
  }
}
