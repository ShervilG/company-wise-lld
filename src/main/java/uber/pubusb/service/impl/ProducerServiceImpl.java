package uber.pubusb.service.impl;

import uber.pubusb.model.Producer;
import uber.pubusb.service.BrokerService;
import uber.pubusb.service.ProducerService;

public class ProducerServiceImpl implements ProducerService {
  private final BrokerService brokerService;

  public ProducerServiceImpl(BrokerService brokerService) {
    this.brokerService = brokerService;
  }

  @Override
  public void produceData(Producer producer, String data, String topicName) {
    System.out.println("Producer " + producer.getProducerName() + " pushing data " + data + " to " + topicName);

    this.brokerService.pushDataToTopic(producer, data, topicName);
  }
}
