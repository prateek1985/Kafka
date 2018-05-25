package kafka.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author Prateek Goel
 */

public class ProducerApp 
{

	private static String topicName = "test";

	public static void main(String[] args) {
		Properties props = new Properties();

		props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);

		String key = "1";
		String value = "prateek";

		ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

		// For Fire and forgot send
		// producer.send(record);

		// async send with callback
		// producer.send(record, new ProducerCallback());

		// sync send
		try {
			RecordMetadata metaData = producer.send(record).get();
			System.out.println("Partition no.: " + metaData.partition() + " Offset: " + metaData.offset());

		} catch (Exception e) {
			System.out.println("Exception occured");
		}
		finally {
			producer.close();
		}
	}
}


class ProducerCallback implements Callback {

	public void onCompletion(RecordMetadata arg0, Exception ex) {
		if (ex != null) {
			System.out.println("Async send failed");
		} else
			System.out.println("Async send success");
	}

}

