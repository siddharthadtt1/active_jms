package org.practice.singlethreaded;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSActiveConsumer {

	public static void main(String[] args) {

		try {
			// Create ActiveMQConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://sid-PC:61616");

			// Create Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create Destination - Queue/Topic
			Destination destination = session.createQueue("HELLO_WORLD.TEST_Q");

			// Create MessageConsumer
			MessageConsumer consumer = session.createConsumer(destination);

			// Receive message from Producer
			Message message = consumer.receive(100);

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("Received : " + textMessage.getText());
			} else {
				System.out.println("Received : " + message);
			}

			// cleanup
			session.close();
			connection.close();

		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

}
