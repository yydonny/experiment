package yd.service

import java.util
import java.util.Optional

import org.springframework.stereotype.Component
import yd.poll.{Poll, PollOption}

import scala.collection.JavaConverters._
import scala.collection.concurrent.TrieMap

@Component
class PollVoteCache {
  val cache = new TrieMap[Poll, Map[PollOption, Int]]
  def put(poll: Poll, snapshot: Map[PollOption, Int]) = cache.put(poll, snapshot)
  def get(poll: Poll) = cache.get(poll)

  def getJava(poll: Poll): Optional[java.util.Map[PollOption, Integer]] = cache.get(poll) match {
    case Some(data) => Optional.of(
      // convert Int to Java Integer inside a hash map
      data.foldLeft(new util.HashMap[PollOption, Integer]()) {
        case (map, (opt, vote)) => map.put(opt, vote); map
      })
    case None => Optional.empty()
  }
}
