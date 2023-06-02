package uber.pubusb.service.impl;

import uber.pubusb.model.Consumer;
import uber.pubusb.service.ConsumerService;

public class ConsumerServiceImpl implements ConsumerService {

  @Override
  public void pushDataToConsumer(Consumer consumer, String topicName, String data) {
    System.out.println(consumer.getConsumerName() + " received data " + data + " from topic " + topicName);
  }
}
