package _book._kafka_authoritative_guide._4_read;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * 消费者 demo
 * @author 734070824@qq.com
 * @date 2019/1/16 14:54
 */
public class ConsumerDemo {
    public static void main(String[] args) {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "192.168.7.204:9092");
        kafkaProps.put("group.id", "Test");
        kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProps);
        consumer.subscribe(Collections.singletonList("Tangqing"));

        try{

            ConsumerRecords<String, String> poll = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : poll) {
                System.err.println(record.topic()+"--" +record.partition()
                        +"--"+record.offset()+"--"+record.key()+"--"+record.value());
            }


        }finally {
            consumer.close();
        }

    }
}
