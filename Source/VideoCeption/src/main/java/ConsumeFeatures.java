import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConsumeFeatures {
        private final ConsumerConnector consumer;
        private final String topic;

        public ConsumeFeatures(String zookeeper, String groupId, String topic) {
            Properties props = new Properties();
            props.put("zookeeper.connect", zookeeper);
            props.put("group.id", groupId);
            props.put("zookeeper.session.timeout.ms", "500");
            props.put("zookeeper.sync.time.ms", "250");
            props.put("auto.commit.interval.ms", "1000");

            consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
            this.topic = topic;
        }

        public void testConsumer() throws UnsupportedEncodingException {
            Map<String, Integer> topicCount = new HashMap<String, Integer>();
            topicCount.put(topic, 1);

            Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
            List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
            for (final KafkaStream stream : streams) {
                ConsumerIterator<byte[], byte[]> it = stream.iterator();
                while (it.hasNext()) {
                    String value = new String(it.next().message(), "UTF-8");
                    System.out.println("Consumed String" + value);
                }
                if (consumer != null) {
                    consumer.shutdown();
                }
            }
        }

        public static void main(String[] args) throws UnsupportedEncodingException {
            String topic = "featuresgeneration"; //Topic Name
            ConsumeFeatures simpleConsumer = new ConsumeFeatures("172.16.2.241:9092", "testgroup", topic);
            simpleConsumer.testConsumer();
        }
    }

