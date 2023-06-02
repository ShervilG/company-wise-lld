package uber.pubusb.service;

import uber.pubusb.model.Consumer;

public interface ConsumerService {
  void pushDataToConsumer(Consumer consumer, String topicName, String data);
}
