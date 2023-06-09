package uber.pubusb.service.impl;

import uber.pubusb.model.Consumer;
import uber.pubusb.model.Producer;
import uber.pubusb.model.Topic;
import uber.pubusb.service.BrokerService;
import uber.pubusb.service.ConsumerService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class BrokerServiceImpl implements BrokerService {
  private final ConcurrentMap<String, Topic> topicNameMap;
  private final ConcurrentMap<String, List<Consumer>> topicNameToConsumerMap;
  private final ConsumerService consumerService;

  public BrokerServiceImpl(ConsumerService consumerService) {
    this.consumerService = consumerService;
    this.topicNameMap = new ConcurrentHashMap<>();
    this.topicNameToConsumerMap = new ConcurrentHashMap<>();
  }

  @Override
  public void pushDataToTopic(Producer producer, String data, String topicName) {
    if (topicNameMap.get(topicName) == null) {
      return;
    }

    try {
      topicNameMap.get(topicName).getQueue().put(data);
    } catch (InterruptedException exception) {
      System.out.println("Exception occurred while adding data to topic: " + topicName + " by producer " +
          producer.getProducerName());
    }
  }

  @Override
  public void registerConsumerForTopic(Consumer consumer, String topicName) {
    if (topicNameMap.get(topicName) == null) {
      return;
    }

    topicNameToConsumerMap.get(topicName).add(consumer);
  }

  @Override
  public void registerTopic(String topicName) {
    Topic newTopic = new Topic().toBuilder()
        .topicId(UUID.randomUUID().toString())
        .topicName(topicName)
        .queue(new LinkedBlockingQueue<>())
        .build();

    this.topicNameMap.putIfAbsent(topicName, newTopic);
    this.topicNameToConsumerMap.putIfAbsent(topicName, new ArrayList<>());

    Thread topicThread = new Thread(() -> listenOnTopicAndPushUpdatesToConsumer(newTopic));
    topicThread.start();
  }

  private void listenOnTopicAndPushUpdatesToConsumer(Topic topic) {
    while (true) {
      try {
       String data = topic.getQueue().take();

       topicNameToConsumerMap.get(topic.getTopicName()).forEach(consumer -> {
         this.consumerService.pushDataToConsumer(consumer, topic.getTopicName(), data);
       });
      } catch (InterruptedException exception) {
        System.out.println("Broker error while listening on topic: " + topic);
      }
    }
  }
}
