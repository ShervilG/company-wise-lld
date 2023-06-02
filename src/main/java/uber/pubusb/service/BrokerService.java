package uber.pubusb.service;

import uber.pubusb.model.Consumer;
import uber.pubusb.model.Producer;

public interface BrokerService {
  void registerTopic(String topic);
  void registerConsumerForTopic(Consumer consumer, String topicName);
  void pushDataToTopic(Producer producer, String data, String topicName);
}
