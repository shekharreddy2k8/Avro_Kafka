/**
 * 
 */
package kafka;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.log4j.Logger;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * @author sangalar
 * @param <TopicConsumer>
 *
 */
class ConsumerThread<TopicConsumer> implements Runnable 
{
	final static Logger logger = Logger.getLogger(ConsumerThread.class);
	private TopicConsumer parent;
	private KafkaStream<byte[], byte[]> stream;
    private int partition;
    private String schema;
    
    public ConsumerThread(TopicConsumer parent, KafkaStream<byte[], byte[]> stream, int partition,String schema) 
    {
        this.partition = partition;
        this.stream = stream;
        this.parent = parent;
        this.schema=schema;
    }
   // public static String avroSchema ="{\"type\" : \"record\",\"name\" : \"sandwich\",  \"fields\" : [ {\"name\" : \"name\",\"type\" : \"string\"}, {\"name\" : \"description\",\"type\" : \"string\" } ]}";
    public void run() 
    {
  	  ConsumerIterator<byte[], byte[]> it = stream.iterator();
  	 // log.debug("Topic consumer for Topic ",parent.topic,",  thread ",partition," is starting to fetch messages...");
  	  while(it.hasNext()){
  		String msg = new String(it.next().message());
  		//System.out.println("Actual message => "+msg);
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(msg.getBytes(), null);
        Schema schema = new Schema.Parser().parse(this.schema);
        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
        GenericRecord result=null;
        GenericData.Record genericRecord = new GenericData.Record(schema);
		try {
			result = reader.read(genericRecord, decoder);
			
			StringBuffer sb=new StringBuffer();
			List<Schema.Field> fieldsArr= schema.getFields();
	        for (Schema.Field field : fieldsArr) {
				sb.append(result.get(field.name()));
				sb.append(",");
			}
			System.out.println(sb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
  		
  		logger.info(msg);
  	  }
    }
}
