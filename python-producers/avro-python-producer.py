from confluent_kafka import Producer
from confluent_kafka.avro import CachedSchemaRegistryClient
from confluent_kafka.avro.serializer.message_serializer import MessageSerializer
import avro
import json

print("Start: avro-python-producer")

props = {
    'client.id': "basic-python-producer",
    'bootstrap.servers': "localhost:9092",
}

topic = "avro-python-producer-topic"

producer = Producer(props)

# connect to the schema_registry
schema_registry = CachedSchemaRegistryClient("http://localhost:8081")

# define avro serde - to be used to encode msg value against the avro schema
avro_serde = MessageSerializer(schema_registry)

# convert json to avro schema
schema = avro.schema.Parse(json.dumps(
    { 
        "namespace": "test.value.avro",
        "type": "record",
        "name": "avroValue",
        "fields": [
            { "name": "name", "type": "string"},
            { "name": "type", "type": "string"}
            ]
    }
))

# io.confluent.kafka.serializers.subject.TopicNameStrategy: (default):
# The subject name for message keys is <topic>-key, and <topic>-value for message values.
# This means that the schemas of all messages in the topic must be compatible with each other.
#  NOTE: KSQL and the Confluent Control Center currently only recognizes this format.

# subject = topic + '-value' # == "avro-python-producer-topic-value"

# io.confluent.kafka.serializers.subject.RecordNameStrategy:
# The subject name is the fully-qualified name of the Avro record type of the message.
# Thus, the schema registry checks the compatibility for a particular record type, regardless of topic.
# This setting allows any number of different event types in the same topic.

subject = schema.fullname  # == "my.test.value"

# io.confluent.kafka.serializers.subject.TopicRecordNameStrategy:
# The subject name is <topic>-<type>, where <topic> is the Kafka topic name, and <type> is the fully-qualified
# name of the Avro record type of the message. This setting also allows any number of event types in the same topic,
# and further constrains the compatibility check to the current topic only.

# subject = topic + '-' + schema.fullname # == "avro-python-producer-topic-my.test.value"

# get registered schema id from the schema_registry
schema_id = schema_registry.register(subject, schema)

for i in range(5):
    key = "key-" + str(i)
    value = "value-" + str(i)
    record_value = avro_serde.encode_record_with_schema_id(
        schema_id=schema_id,
        record={"name": value, "type": "avro"},
        is_key=False,
    )
    producer.produce(topic, key=key.encode(
        'utf-8'), value=record_value)
    print("Produced:", key, record_value)

producer.flush()

print("End: avro-python-producer")
