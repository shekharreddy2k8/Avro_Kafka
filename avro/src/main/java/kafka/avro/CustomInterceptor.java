/**
 * 
 */
package kafka.avro;

/**
 * @author sangalar
 *
 */
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

/**
 * @author  Sangala Shakhar Reddy
 *
 */
public class CustomInterceptor implements Interceptor {

	private String avropath;
	public CustomInterceptor(String path){
		this.avropath=path;
	}

	@Override
	public Event intercept(Event event) {

		byte[] eventBody = event.getBody();
		try {
			
			
			byte[] bytes=new byte[1024];
			FileInputStream fis = new FileInputStream(this.avropath);
			fis.read(bytes);
			//String avroSchema ="{\"type\" : \"record\",\"name\" : \"sandwich\",  \"fields\" : [ {\"name\" : \"name\",\"type\" : \"string\"}, {\"name\" : \"description\",\"type\" : \"string\" } ]}";
			Schema schema = new Schema.Parser().parse(fis);
	        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(eventBody, null);
	        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
	        GenericRecord result = reader.read(null, decoder);
	        StringBuffer sb=new StringBuffer();
	        
	       List<Schema.Field> fieldsArr= schema.getFields();
	        for (Schema.Field field : fieldsArr) {
				sb.append(result.get(field.name()));
				sb.append(",");
			}
	        
	        event.setBody(sb.toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}

		return event;
	}

	@Override
	public void close() {

	}

	@Override
	public void initialize() {

	}

	@Override
	public List intercept(List events) {
		for (Iterator iterator = events.iterator(); iterator.hasNext();) {
			Event next = intercept((Event) iterator.next());
			if (next == null) {
				iterator.remove();
			}
		}
		return events;
	}

	public static class Builder implements Interceptor.Builder {
		String avropath=null;
		
		@Override
		public void configure(Context context) {
			this.avropath = context.getString("path");
		}

		@Override
		public Interceptor build() {
			return new CustomInterceptor(this.avropath);
		}
	}

}
