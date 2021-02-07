package com.satyajit.consumer;

//import all the packages required to fetch the message from the cluster

import com.satyajit.model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        ConsumerListener c = new ConsumerListener();
        Thread thread = new Thread(c);
        thread.start();
    }

    public static void consumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092"); //method to run on the specific localhost and fetch from the cluster on specific host

        //deserializing the key using the kafka package
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //deserializing the value to get the value from a cluster
        properties.put("value.deserializer", "com.satyajit.deserializer.UserDeserializer");

        //create a group-id
        properties.put("group.id", "test-group");

        KafkaConsumer<String, User> kafkaConsumer = new KafkaConsumer(properties);

        //create a variable to store the topics
        List topics = new ArrayList();

        //name a topic
        topics.add("user");

        //subscribe or call to the topic
        kafkaConsumer.subscribe(topics);
        try {
            //Make a file to store the message
            FileWriter file = new FileWriter("objects.txt");
            while (true) {
                //enable the append to write into a file
                FileWriter fileWriter = new FileWriter("objects.txt", true);

                //create the poll to check for the message after every particular time
                ConsumerRecords<String, User> records = kafkaConsumer.poll(1000);
                ObjectMapper mapper = new ObjectMapper();
                for (ConsumerRecord<String, User> record : records) {

                    //show the message and write into a file and print
                    System.out.println("Message Received Successfully" + "\n" + mapper.writeValueAsString(record.value()));

                    // method that would be writing this value to a file.
                    fileWriter.append(mapper.writeValueAsString(record.value()) + "\n");
                }
                fileWriter.close(); //close the filewriter after finishing the operation
            }
        } catch (Exception e) {   //method to print the exception
            System.out.println(e.getMessage());
        } finally {
            kafkaConsumer.close(); //method to close the consumer server
        }
    }
}

class ConsumerListener implements Runnable {


    @Override
    public void run() {
        Consumer.consumer();
    }
}
