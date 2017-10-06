package org.practice.singlethreaded;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSActiveProducer {

	public static void main(String[] args) {

		try {
			// create ActiveMQConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://sid-PC:61616");

			// create Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// create Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// create Destination - Topic/Queue
			Destination destination = session.createQueue("HELLO_WORLD.TEST_Q");

			// create Message Producer
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			String message = "Sending hello world from Thread : " + Thread.currentThread().getName();
			TextMessage textMessage = session.createTextMessage(message);

			// Tell the producer to send a message
			System.out.println("Sending message : " + message);
			producer.send(textMessage);

			// clean up
			session.close();
			connection.close();

		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

}
