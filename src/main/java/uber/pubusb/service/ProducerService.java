package uber.pubusb.service;

import uber.pubusb.model.Producer;

public interface ProducerService {
  void produceData(Producer producer, String data, String topicName);
}
