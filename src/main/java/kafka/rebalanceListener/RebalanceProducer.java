package kafka.rebalanceListener;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class RebalanceProducer {
	public static void main(String[] args) throws InterruptedException {

		String topicName = "RebalanceTopic";
		String msg;

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);
		try {
			while (true) {
					msg = "hello";
					producer.send(new ProducerRecord<String, String>(topicName, 0, "Key", msg)).get();

					msg = "world";
					producer.send(new ProducerRecord<String, String>(topicName, 1, "Key", msg)).get();
			}
		} catch (Exception ex) {
			System.out.println("Intrupted");
		} finally {
			producer.close();
		}

	}
}
