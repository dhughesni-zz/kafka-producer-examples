from confluent_kafka import Producer

print("Start: basic-python-producer")

props = {
    'client.id': "basic-python-producer",
    'bootstrap.servers': "localhost:9092",
}

producer = Producer(props)

topic = "basic-python-producer-topic"

for i in range(5):
    key = "key-" + str(i)
    value = "value-" + str(i)
    producer.produce(topic, key=key.encode(
        'utf-8'), value=value.encode('utf-8'))
    print("Produced:", key, value)

producer.flush()

print("End: basic-python-producer")
