package yd.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import yd.poll.Poll;
import yd.poll.PollOption;
import yd.service.PollService;
import yd.service.PollVoteCache;
import yd.service.PollVoteSerializer;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/poll")
class PollController {
  private static final Logger logger = LoggerFactory.getLogger(PollController.class);

  private final PollService pollService;
  private final PollVoteCache voteCache;
  private final PollVoteSerializer serializer;

  @Autowired
  public PollController(PollService pollService, PollVoteCache voteCache, PollVoteSerializer serializer) {
    this.pollService = pollService;
    this.voteCache = voteCache;
    this.serializer = serializer;
  }

  private Poll findPoll(int pollId) {
    if (pollId == 2) {
      return pollService.predefinedPolls().last();
    } else {
      return pollService.predefinedPolls().head();
    }
  }

  @RequestMapping("/{pollId}")
  public String poll(@PathVariable int pollId, Model model) {
    Poll poll = findPoll(pollId);
    pollService.createPoll(poll);
    model.addAttribute("poll", poll);
    return "poll/poll";
  }

  @RequestMapping(value = "/{pollId}/{optionId}",
      method = RequestMethod.POST)
  public ResponseEntity<String> vote(@PathVariable int pollId,
                     @PathVariable int optionId) {
    Poll poll = findPoll(pollId);
    pollService.vote(poll, poll.option(optionId).getOrElse(null));
    pollService.takeSnapshot(poll);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping("/{pollId}/votes")
  public ResponseEntity<String> snapshot(@PathVariable int pollId) {
    Poll poll = findPoll(pollId);
    try {
      Optional<Map<PollOption, Integer>> votes = voteCache.getJava(poll);
      if (votes.isPresent()) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(
            serializer.serialize(votes.get()),
            headers, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (JsonProcessingException e) {
      logger.error("JSON serialization error", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
