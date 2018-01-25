package kafka.avro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.hadoop.io.IntWritable;

public class AvroWriter {
	
	public static void main2(String[] args) throws Exception {
		
		User user1 = new User();
		user1.setName("Alyssa");
		user1.setFavoriteNumber(256);
		user1.setFavoriteColor("red");
		// Leave favorite color null
		
		User user2 = new User();
		user2.setName("Alyssa2");
		user2.setFavoriteNumber(2);
		user2.setFavoriteColor("red2");
		
		User user3 = new User();
		user3.setName("Alyssa2");
		user3.setFavoriteNumber(2);
		user3.setFavoriteColor("red2");

				
		DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
		DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
		//dataFileWriter.create(user1.getSchema(), new File("users.avro"));
		
		/*dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.append(user3);
		dataFileWriter.close();*/
		
	}
	public byte[] serialize() throws IOException{
		
	      //Instantiating the IntWritable object
	      IntWritable intwritable = new IntWritable(12);
	   
	      //Instantiating ByteArrayOutputStream object
	      ByteArrayOutputStream byteoutputStream = new ByteArrayOutputStream();
	   
	      //Instantiating DataOutputStream object
	      DataOutputStream dataOutputStream = new
	      DataOutputStream(byteoutputStream);
	   
	      //Serializing the data
	      intwritable.write(dataOutputStream);
	   
	      //storing the serialized object in bytearray
	      byte[] byteArray = byteoutputStream.toByteArray();
	   
	      //Closing the OutputStream
	      dataOutputStream.close();
	      return(byteArray);
	   }
		
	   public static void main3(String args[]) throws IOException{
		   AvroWriter serialization= new AvroWriter();
		   FileOutputStream fos = new FileOutputStream("user.avro");
		   fos.write(serialization.serialize());
		   fos.close();
	      System.out.println("done");
	   }
	   
	   public void deserialize(byte[]byteArray) throws Exception{
		   
		      //Instantiating the IntWritable class
		      IntWritable intwritable =new IntWritable();
		      
		      FileInputStream fos = new FileInputStream("user.avro");
		      
		      byte []b=new byte[1024];
    		  fos.read(b);
		      
		      //Instantiating ByteArrayInputStream object
		      ByteArrayInputStream InputStream = new ByteArrayInputStream(b);
		      int i=InputStream.read();
		      
		      //Instantiating DataInputStream object
		      DataInputStream datainputstream=new DataInputStream(InputStream);
		      
		      //deserializing the data in DataInputStream
		      intwritable.readFields(datainputstream);
		      
		      //printing the serialized data
		      //System.out.println((intwritable).get());
		      
		      System.out.println(i);
		   }
		   
		   public static void main(String args[]) throws Exception {
			   AvroWriter dese = new AvroWriter();
			   FileOutputStream fos = new FileOutputStream("user.avro");
			   fos.write(new AvroWriter().serialize());
			   fos.close();
		      dese.deserialize(null);
		   }

}

