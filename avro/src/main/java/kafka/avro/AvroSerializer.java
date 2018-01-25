package kafka.avro;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

/**
 * Created by Sharkhar Reddy
 */
public class AvroSerializer {

	public static String avroSchema ="{\"type\" : \"record\",\"name\" : \"sandwich\",  \"fields\" : [ {\"name\" : \"name\",\"type\" : \"string\"}, {\"name\" : \"description\",\"type\" : \"string\" } ]}";
    
    protected DatumReader<GenericRecord> reader;

    protected Schema schema;

	//public static byte[] serialize(Schema avroSchema, List<Map<String, Object>> recordList) throws Exception {
    public static void main(String[] args) throws Exception {
		
			Schema schema = new Schema.Parser().parse(avroSchema);
			List<Schema.Field> fs=schema.getFields();
			for (Schema.Field field : fs) {
				System.out.println(field.name());
			}
			
			
		    DatumWriter<GenericRecord> writer = new GenericDatumWriter(schema);
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
	        
	        GenericData.Record genericRecord = new GenericData.Record(schema);
	        genericRecord.put("name" ,"Ben");
	        genericRecord.put("description" ,"Ben dec");
	        writer.write(genericRecord, encoder);
	        encoder.flush();
	        FileOutputStream fos = new FileOutputStream("user1.avro");
			fos.write(out.toByteArray());
			fos.close();
			
			byte[] bytes=new byte[1024];
			FileInputStream fis = new FileInputStream("user1.avro");
			fis.read(bytes);
	        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
	        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
	        GenericRecord result = reader.read(null, decoder);
	        System.out.println(result.get("name"));
	        System.out.println(result.get("description"));
	    }
}
