import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;

public class ProducerDemo2 {

    public static void main(String[] args) {
        boolean isAsync =  args.length == 0 || args[0].trim().equalsIgnoreCase("sync");
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("client.id", "producerDemo2");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String,String>(props);
        List<PartitionInfo> partitionInfos = producer.partitionsFor("new-topic");
        for(final PartitionInfo partitionInfo : partitionInfos) {
            System.out.println(partitionInfo.toString());
        }
        String topic = "kafka-topic-02";
        for (int i = 0; i < 10; i++) {
            String message = UUID.randomUUID().toString();
            long startTime = System.currentTimeMillis();
            if (isAsync) {
                producer.send(new ProducerRecord<String, String>(topic, String.valueOf(i), message),
                        new ProducerAckCallback(startTime, i, message));
            } else {
                try {
                    /** 同步等待返回 */
                    producer.send(new ProducerRecord<String, String>(topic, String.valueOf(i), message)).get();
                    System.out.println("Send message : " + "key:" + i + ";value:" + message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        producer.close();
    }

    static class ProducerAckCallback implements Callback {

        private final Long startTime;

        private final int key;

        private final String value;

        public ProducerAckCallback(Long startTime, int key, String value) {
            this.startTime = startTime;
            this.key = key;
            this.value = value;
        }

        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
            long spendTime = System.currentTimeMillis() - startTime;
            if (null != metadata) {
                System.out.println("消息(" + key + "," + value + ")send to partition " + metadata.partition()
                        + " and offest " + metadata.offset() + " and spend  " + spendTime + " ms");

            }
        }
    }
}