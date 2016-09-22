package yd.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;
import yd.poll.PollOption;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Component
public class PollVoteSerializer {
  private final ObjectMapper mapper = new ObjectMapper();

  public PollVoteSerializer() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(PollVoteCount.class, new PollVoteSerializer0());
    mapper.registerModule(module);
  }

  public String serialize(Map<PollOption, Integer> votes) throws JsonProcessingException {
    return mapper.writeValueAsString(new PollVoteCount(votes));
  }

  private static class PollVoteCount {
    private final Map<PollOption, Integer> votes;
    PollVoteCount(Map<PollOption, Integer> votes) {
      this.votes = votes;
    }
    Collection<Map.Entry<PollOption, Integer>> entries() {
      return votes.entrySet();
    }
  }

  private static class PollVoteSerializer0 extends JsonSerializer<PollVoteCount> {
    @Override
    public void serialize(PollVoteCount voteCount, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeStartObject();
      for (Map.Entry<PollOption, Integer> entry: voteCount.entries()) {
        gen.writeNumberField(String.valueOf(entry.getKey().id()), entry.getValue());
      }
      gen.writeEndObject();
    }
  }
}
