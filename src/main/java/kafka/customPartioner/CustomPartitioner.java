package kafka.customPartioner;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;

public class CustomPartitioner implements Partitioner {

	// inialization
	public void configure(Map<String, ?> configs) {}

	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		int partionNo = 0;

		if ((keyBytes == null) || (!(key instanceof String)))
			throw new InvalidRecordException("All messages must have string key");

		if (((String) key).startsWith("SF"))
			partionNo = (new Random().nextInt(2)) % numPartitions;
		else
			partionNo = ((new Random().nextInt(8)) + 2) % numPartitions;

		return partionNo;
}

	// clean up
	public void close() {}
}
