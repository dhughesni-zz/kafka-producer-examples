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

# BASIC-JAVA-PRODUCER
## Project Setup: basic-java-producer
```
$ mvn -B archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=com.dh.app -DartifactId=basic-java-producer
```
- https://docs.confluent.io/current/clients/install.html#installation-maven
## To Run: basic-java-producer
```
kafka-producer-example/basic-java-producer $ mvn clean compile exec:java -Dexec.mainClass="com.dh.app.App"
```

---

# BASIC-PYTHON-PRODUCER
## Project Setup: basic-python-producer
```
kafka-producer-example $ mkdir basic-python-producer
<!-- create virtual env -->
kafka-producer-example/basic-python-producer $ python3 -m venv venv
<!-- source venv -->
kafka-producer-example/basic-python-producer $ source venv/bin/activate
<!-- install confluent_kafka -->
kafka-producer-example/basic-python-producer $ pip install confluent_kafka
<!-- export to requirements.txt -->
kafka-producer-example/basic-python-producer $ pip freeze > requirements.txt
<!-- make the app file -->
kafka-producer-example/basic-python-producer $ touch app.py
```
## To Run: basic-python-producer
```
kafka-producer-example/basic-python-producer $ python3 -m venv venv
kafka-producer-example/basic-python-producer $ source venv/bin/activate
kafka-producer-example/basic-python-producer $ pip install -r requirements.txt
kafka-producer-example/basic-python-producer $ python3 app.py
```