package yd.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import yd.config.PollSystemBean
import yd.poll.{Poll, PollOption, polls}

@Component
class PollService @Autowired() (system: PollSystemBean) {
  import yd.ak.PollManager._
  val pollManager = system.pollManager

  def predefinedPolls: Seq[Poll] = Seq(polls.poll1, polls.poll2)
  def createPoll(poll: Poll) = pollManager ! CreatePoll(poll)
  def vote(poll: Poll, pollOption: PollOption) = pollManager ! Vote(poll, pollOption)
  def takeSnapshot(poll: Poll) = pollManager ! SnapShot(poll)
}
