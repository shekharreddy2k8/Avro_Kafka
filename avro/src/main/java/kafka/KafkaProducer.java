/**
 * 
 */
package kafka;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
//import simple producer packages
import org.apache.kafka.clients.producer.Producer;
//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;
/**
 * @author sangalar
 *
 */
public class KafkaProducer {
	
    protected DatumReader<GenericRecord> reader;

	public static void main(String[] args)throws Exception {

		
		FileReader reader = null;
		if(args.length==0){
			System.out.println("Kafka properties path not mentioned. Reading default kafka producer.properties file from current directory ");
			reader = new FileReader("producer.properties");
		}else{
			//reader = new FileReader("C:/Code/MAS/avro/src/main/resources/kafka.properties");
			reader = new FileReader(args[0]);
		}

		Properties props = new Properties();
		props.load(reader);
		
		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", props.getProperty("bootstrap.servers"));
		kafkaProps.put("retries", props.getProperty("retries"));
        //string inputs and outputs
		kafkaProps.put("acks", "all");
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		System.out.println("Avro schema: "+props.getProperty("avro.schema"));
		Schema schema = new Schema.Parser().parse(props.getProperty("avro.schema"));
		
		int rows=Integer.parseInt(props.getProperty("numberof.rows.to.send")!=null?props.getProperty("numberof.rows.to.send"):"10");
		
	     Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(kafkaProps);
	      for(int i = 0; i < rows; i++){
	    	DatumWriter<GenericRecord> writer = new GenericDatumWriter(schema);
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
	        
	        GenericData.Record genericRecord = new GenericData.Record(schema);
	        //genericRecord.put("name" ,"Ben");
	        //genericRecord.put("description" ,"Ben dec");
	        
	        List<Schema.Field> fieldsArr= schema.getFields();
	        for (Schema.Field field : fieldsArr) {
				 genericRecord.put(field.name() ,field.name()+"-value-"+i);
			}
	        
	        writer.write(genericRecord, encoder);
	        encoder.flush();
	        // producer.send(new ProducerRecord<String, String>(props.getProperty("kafka.topic"), Integer.toString(i), Integer.toString(i)));
	         
	         producer.send(new ProducerRecord<String, String>(props.getProperty("kafka.topic"), 
	 	            Integer.toString(i), new String(out.toByteArray())));
	               System.out.println(new String(out.toByteArray()));
	   }
	      
	      producer.close();
	}
        
}