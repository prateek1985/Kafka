package kafka.customPartioner;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerForCustomPartioner {

   public static void main(String[] args) throws Exception{

		String topicName = "CustomPartitionerTopic";

      Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
      props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("partitioner.class", "kafka.customPartioner.CustomPartitioner");

		Producer<String, String> producer = new KafkaProducer<>(props);

		for (int i = 0; i < 10; i++) {
			RecordMetadata data = producer.send(new ProducerRecord<>(topicName, "OE" + i, "OE" + i)).get();
			System.out.println("Key = OE" + i + " Partition = " + data.partition());
		}

		for (int i = 0; i < 10; i++) {
			RecordMetadata data = producer.send(new ProducerRecord<>(topicName, "SF" + i, "SF" + i)).get();
			System.out.println("Key = SF" + i + " Partition = " + data.partition());
		}

		producer.close();

   }
}
