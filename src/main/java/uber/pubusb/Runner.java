package uber.pubusb;

import uber.pubusb.model.Consumer;
import uber.pubusb.model.Producer;
import uber.pubusb.service.BrokerService;
import uber.pubusb.service.ConsumerService;
import uber.pubusb.service.ProducerService;
import uber.pubusb.service.impl.BrokerServiceImpl;
import uber.pubusb.service.impl.ConsumerServiceImpl;
import uber.pubusb.service.impl.ProducerServiceImpl;

import java.util.Random;
import java.util.UUID;

public class Runner {

  public static void main(String[] args) {
    ConsumerService consumerService = new ConsumerServiceImpl();
    BrokerService brokerService = new BrokerServiceImpl(consumerService);
    ProducerService producerService =  new ProducerServiceImpl(brokerService);

    Consumer consumer = new Consumer().toBuilder()
        .consumerId(UUID.randomUUID().toString())
        .consumerName("Shervil")
        .build();
    Consumer secondConsumer = new Consumer().toBuilder()
        .consumerId(UUID.randomUUID().toString())
        .consumerName("Envy")
        .build();
    Consumer thirdConsumer = new Consumer().toBuilder()
        .consumerId(UUID.randomUUID().toString())
        .consumerName("Third Consumer")
        .build();
    Producer producer = new Producer().toBuilder()
        .producerId(UUID.randomUUID().toString())
        .producerName("Producer 1")
        .build();
    Producer secondProducer = new Producer().toBuilder()
        .producerId(UUID.randomUUID().toString())
        .producerName("Producer 2")
        .build();

    brokerService.registerTopic("TestTopic");
    brokerService.registerConsumerForTopic(consumer, "TestTopic");
    brokerService.registerConsumerForTopic(secondConsumer, "TestTopic");

    Thread firstProducerThread = new Thread(() -> {
      while (true) {
        try {
          producerService.produceData(producer, String.valueOf(new Random().nextInt()), "TestTopic");

          Thread.sleep(1000);
        } catch (InterruptedException exception) {}
      }
    });
    Thread secondProducerThread = new Thread(() -> {
      while (true) {
        try {
          producerService.produceData(secondProducer, String.valueOf(new Random().nextInt()), "TestTopic");

          Thread.sleep(1000);
        } catch (InterruptedException exception) {}
      }
    });

    firstProducerThread.start();
    secondProducerThread.start();

    // Add a third consumer after a while.
    try {
      Thread.sleep(7000);

      brokerService.registerConsumerForTopic(thirdConsumer, "TestTopic");
    } catch (Exception e) {}
  }
}
