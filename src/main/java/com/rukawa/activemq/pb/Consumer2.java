package com.rukawa.activemq.pb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer2 {

	private ConnectionFactory connectionFactory;

	private Connection connection;

	private Session session;

	private MessageConsumer consumer;

	public Consumer2() {

		try {
			this.connectionFactory = new ActiveMQConnectionFactory(
					"digu",  //ActiveMQConnectionFactory.DEFAULT_USER
					"password",  //ActiveMQConnectionFactory.DEFAULT_PASSWORD
					"tcp://localhost:61616");

			this.connection = this.connectionFactory.createConnection();
			connection.start();

			this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); //不启用事务，自动签收

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void receive() {
		try {
			Destination destination = this.session.createTopic("topic1");
			consumer = this.session.createConsumer(destination);
			consumer.setMessageListener(new Listener());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	class Listener implements MessageListener {

		@Override
		public void onMessage(Message msg) {
			try {
				if(msg instanceof TextMessage) {
					System.out.println("c2接收到消息：---------------");
					TextMessage textMessage = (TextMessage) msg;
					System.out.println(textMessage.getText());
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Consumer2 consumer2 = new Consumer2();
		consumer2.receive();
	}
}
