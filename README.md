# Avro_Kafka

Check out and build using maven
mvn clean install

Out put will be avro-0.0.1-SNAPSHOT-kafka.zip in target folder

This zip has both Producer and Consumer for avro.  

Producer will sends 10 messages to kafka topic in avro format and ends program.
You can configure how many rows you want to send to topic using “numberof.rows.to.send” in producer.properties file. If you don’t configure it will send 10  rows. 
Sent will message will be displayed in avro format as show producer output.

Consumer reads same data and de-serialize and print in console. This program will be alive until we kill this process.

Steps to use Producer:

1.	Extract attached zip
2.	Update below properties in producer.properties with proper values.

zookeeper.connect=localhost:2181
bootstrap.servers=localhost:9092
kafka.topic=test
avro.schema={\"type\" : \"record\",\"name\" : \"sandwich\",  \"fields\" : [ {\"name\" : \"name\",\"type\" : \"string\"}, {\"name\" : \"description\",\"type\" : \"string\" } ]}
numberof.rows.to.send=10


3.	Run below command to run consumer program. this will produce 10 rows to given topic
java kafka.KafkaProducer

Example output:
Kafka properties path not mentioned. Reading default kafka producer.properties file from current directory 
Avro schema: {"type" : "record","name" : "sandwich",  "fields" : [ {"name" : "name","type" : "string"}, {"name" : "description","type" : "string" } ]}
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/C:/Users/sangalar/.m2/repository/org/slf4j/slf4j-log4j12/1.6.1/slf4j-log4j12-1.6.1.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/C:/Users/sangalar/.m2/repository/org/apache/avro/avro-tools/1.7.7/avro-tools-1.7.7.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
name-value-0&description-value-0
name-value-1&description-value-1
name-value-2&description-value-2
name-value-3&description-value-3
name-value-4&description-value-4
name-value-5&description-value-5
name-value-6&description-value-6
name-value-7&description-value-7
name-value-8&description-value-8
name-value-9&description-value-9
Picked up _JAVA_OPTIONS: -Djava.net.preferIPv4Stack=true

Steps to use consumer:

1.	Extract attached zip
2.	Update below properties in kafka.properties with proper values.

zookeeper.connect=localhost:2181
bootstrap.servers=localhost:9092
kafka.topic=test
avro.schema={\"type\" : \"record\",\"name\" : \"sandwich\",  \"fields\" : [ {\"name\" : \"name\",\"type\" : \"string\"}, {\"name\" : \"description\",\"type\" : \"string\" } ]}

3.	Run below command to run consumer program. this will
java kafka.KafkaConsumer

Example output:
Kafka properties path not mentioned. Reading default kafka properties file from current directory 
2018-01-24 17:42:57 WARN  VerifiableProperties:85 - Property bootstrap.servers is not valid
2018-01-24 17:42:57 WARN  VerifiableProperties:85 - Property enable.auto.commit is not valid
2018-01-24 17:42:57 WARN  VerifiableProperties:85 - Property key.deserializer is not valid
2018-01-24 17:42:57 WARN  VerifiableProperties:85 - Property value.deserializer is not valid
topic:test
kafka properties: {key.deserializer=org.apache.kafka.common.serialization.StringDeserializer, auto.offset.reset=largest, bootstrap.servers=localhost:9092, enable.auto.commit=false, group.id=test-group, zookeeper.connect=localhost:2181, value.deserializer=org.apache.kafka.common.serialization.StringDeserializer}
Schema : {"type" : "record","name" : "sandwich",  "fields" : [ {"name" : "name","type" : "string"}, {"name" : "description","type" : "string" } ]}
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/C:/Users/sangalar/.m2/repository/org/slf4j/slf4j-log4j12/1.6.1/slf4j-log4j12-1.6.1.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/C:/Users/sangalar/.m2/repository/org/apache/avro/avro-tools/1.7.7/avro-tools-1.7.7.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
2018-01-24 17:42:58 WARN  RangeAssignor:85 - No broker partitions consumed by consumer thread test-group_INBANN0H328806-1516795977937-5fac091b-1 for topic test
name-value-0,description-value-0,
name-value-1,description-value-1,
name-value-2,description-value-2,
name-value-3,description-value-3,
name-value-4,description-value-4,
name-value-5,description-value-5,
name-value-6,description-value-6,
name-value-7,description-value-7,
name-value-8,description-value-8,
name-value-9,description-value-9,



Happy Learning!!!!!!!.

Cheers,
