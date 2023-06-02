package uber.pubusb.model;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Topic {
  private String topicId;
  private String topicName;

  private List<Consumer> consumerList;
  private BlockingQueue<String> queue;
}
