# kafka-producer-examples
Basic Apache Kafka Producer Examples

## References
- https://docs.confluent.io/current/clients/producer.html#ak-java-producer
- https://docs.confluent.io/current/clients/confluent-kafka-python/


# *Start Confluent Stack*
```
$ curl -O http://packages.confluent.io/archive/5.2/confluent-5.2.1-2.12.zip
$ unzip confluent-5.2.1-2.12.zip
$ ./confluent-5.2.1/bin/confluent destroy
$ ./confluent-5.2.1/bin/confluent start
```

# java-producers
## Project Setup: java-producers
```
$ mvn -B archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=com.dh.app -DartifactId=java-producers
```
- https://docs.confluent.io/current/clients/install.html#installation-maven
## To Run: java-producers
```
kafka-producer-example/java-producers $ mvn clean compile exec:java -Dexec.mainClass="com.dh.app.BasicProducer"
<!-- Examples included for the following naming strategies:
- io.confluent.kafka.serializers.subject.TopicNameStrategy
- io.confluent.kafka.serializers.subject.RecordNameStrategy
- io.confluent.kafka.serializers.subject.TopicRecordNameStrategy  -->
kafka-producer-example/java-producers $ mvn clean compile exec:java -Dexec.mainClass="com.dh.app.AvroProducer"
```

---

# python-producers
## Project Setup: python-producers
```
kafka-producer-example $ mkdir python-producers
<!-- create virtual env -->
kafka-producer-example/python-producers $ python3 -m venv venv
<!-- source venv -->
kafka-producer-example/python-producers $ source venv/bin/activate
<!-- install dependencies -->
kafka-producer-example/python-producers $ pip install confluent_kafka
<!-- export to requirements.txt -->
kafka-producer-example/python-producers $ pip freeze > requirements.txt
<!-- make the app files -->
kafka-producer-example/python-producers $ touch basic-python-producer.py
kafka-producer-example/python-producers $ touch avro-python-producer.py
```
## To Run: python-producers
```
kafka-producer-example/python-producers $ python3 -m venv venv
kafka-producer-example/python-producers $ source venv/bin/activate
kafka-producer-example/python-producers $ pip install -r requirements.txt
kafka-producer-example/python-producers $ python3 basic-python-producer.py
<!-- Examples included for the following naming strategies:
- io.confluent.kafka.serializers.subject.TopicNameStrategy
- io.confluent.kafka.serializers.subject.RecordNameStrategy
- io.confluent.kafka.serializers.subject.TopicRecordNameStrategy  -->
kafka-producer-example/python-producers $ python3 avro-python-producer.py
```