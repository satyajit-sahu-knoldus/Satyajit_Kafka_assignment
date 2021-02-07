package com.satyajit.producer;

//import all the packages required to send the value to a cluster

import com.satyajit.model.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class Producer {
    public static void main(String[] args) {

        Properties properties = new Properties();

        //method to run on the specific localhost and fetch from the cluster on specific host
        properties.put("bootstrap.servers", "localhost:9092");

        //serializing the key using the kafka package
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //serializing the value to send the value to a cluster
        properties.put("value.serializer", "com.satyajit.serializer.UserSerializer");

        //method to store the properties and to produce it
        KafkaProducer<String, User> producer = new KafkaProducer<>(properties);

        try {
            Random random = new Random(); //creating a random method to make the age random for every user
            for (int i = 1; i <= 5; i++) {

                //creating user method to give the values
                User user = new User(i, "User" + i, random.nextInt(5) + 20, "B.tech");

                //sending the value toa cluster
                producer.send(new ProducerRecord<String, User>("user", String.valueOf(i), user));

                //printing the sent value
                System.out.println("Message " + user.toString() + " sent !!");
            }
        } catch (Exception e) {
            //print the exception message if any
            e.printStackTrace();
        } finally {
            //finally close the producer after sending the message to the cluster
            producer.close();
        }
    }
}
