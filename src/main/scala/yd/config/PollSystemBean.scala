package yd.config

import javax.annotation.PreDestroy

import akka.actor.{ActorRef, ActorSystem, Props}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import yd.ak.PollManager

@Component
class PollSystemBean @Autowired()(appctx: ApplicationContext) {
  private val system = ActorSystem("polls")
  AppContextExtension(system).applicationContext = appctx

  lazy val pollManager: ActorRef = system.actorOf(Props(classOf[PollManager]), "manager")

  @PreDestroy
  def shutdown(): Unit = system.terminate()
}
