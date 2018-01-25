package kafka;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.log4j.Logger;

public class KafkaConsumer {
	
	final static Logger logger = Logger.getLogger(KafkaConsumer.class);

	public static void main(String[] args) throws Exception {

		FileReader reader = null;
		if(args.length==0){
			System.out.println("Kafka properties path not mentioned. Reading default kafka properties file from current directory ");
			reader = new FileReader("kafka.properties");
		}else{
			//reader = new FileReader("C:/Code/MAS/avro/src/main/resources/kafka.properties");
			reader = new FileReader(args[0]);
		}
		
		Properties props = new Properties();
		props.load(reader);
		
		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", props.getProperty("bootstrap.servers"));
		kafkaProps.put("zookeeper.connect", props.getProperty("zookeeper.connect"));
		kafkaProps.put("group.id",props.getProperty("group.id"));
        //using auto commit
		kafkaProps.put("enable.auto.commit", props.getProperty("enable.auto.commit"));
		kafkaProps.put("auto.offset.reset", props.getProperty("auto.offset.reset"));
		
        //string inputs and outputs
		kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		ConsumerConfig cc = new ConsumerConfig(kafkaProps);
		
		String topic=props.getProperty("kafka.topic");
		System.out.println("topic:" + topic);
		System.out.println("kafka properties: "+ kafkaProps);
		
		logger.info("topic:" + topic);
		logger.info("kafka properties: "+ kafkaProps);
		logger.info("Schema: "+ props.getProperty("auto.offset.reset"));
		System.out.println("Schema : "+ props.getProperty("avro.schema"));
		
		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(cc);
		
		
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		// topicCountMap.put(topic, new Integer(numberOfThreads));
		topicCountMap.put(topic, new Integer(2));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

		//Executors executor = Executors.newFixedThreadPool(numberOfThreads);
		int numberofThreads=props.get("kafka.consumer.threads")!=null?Integer.parseInt(props.getProperty("kafka.consumer.threads")):1;
		ExecutorService executor = Executors.newFixedThreadPool(numberofThreads);
		int threadNumber = 0;
		for (final KafkaStream<byte[], byte[]> stream : streams) {
			executor.submit(new ConsumerThread(null, stream, threadNumber,props.getProperty("avro.schema")));
			threadNumber++;
		}
		
		//Thread.sleep(20000);
	}
}
