import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.IOException;
import java.util.Properties;

public class SendFeatures {
    public static void ProduceFeatures() throws IOException {
        String topic = "featuresgeneration";

        Producer<Integer, String> producer;
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "172.16.2.241:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        producer = new Producer<Integer, String>(new ProducerConfig(properties));

        System.out.println("Key Frames Generated");
        String fileName = "output/features.txt";
        EncodingFeatures ed = new EncodingFeatures();
        String encStr = ed.encodeToString(fileName);
        System.out.println("encStr: " + encStr);
        String fileNameLength = Integer.toString(fileName.length());
        System.out.println("Simple Filename Length: " + fileNameLength);
        String encStrFName = fileName + ";" + encStr;
        System.out.println("Encoded String: " + encStrFName.length());
        //String Sample = "Hello";
        KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, encStrFName);//Encoding the Video
        System.out.println("Data is:"+ data);
        producer.send(data);
        System.out.println("Message Sent");
    }
}
