from confluent_kafka import Producer

print("Start: basic-java-producer")

props = {
    'client.id': "basic-java-producer",
    'bootstrap.servers': "localhost:9092",
    # 'key.serializer': "org.apache.kafka.common.serialization.StringSerializer",
    # 'value.serializer': "org.apache.kafka.common.serialization.StringSerializer"
}

producer = Producer(props)

topic = "basic-python-producer-topic"

for i in range(5):
    key = "key-" + str(i)
    value = "value-" + str(i)
    producer.produce(topic=topic, key=key.encode(
        'utf-8'), value=value.encode('utf-8'))
    print("Produced:", key, value)

print("End: basic-java-producer")
