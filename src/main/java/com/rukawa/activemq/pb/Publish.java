package com.rukawa.activemq.pb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publish {

	private ConnectionFactory connectionFactory;

	private Connection connection;

	private Session session;

//	private Destination destination;

	private MessageProducer producer;

	public Publish() {

		try {
			this.connectionFactory = new ActiveMQConnectionFactory(
					"digu",  //ActiveMQConnectionFactory.DEFAULT_USER
					"password",  //ActiveMQConnectionFactory.DEFAULT_PASSWORD
					"tcp://localhost:61616");

			this.connection = this.connectionFactory.createConnection();
			connection.start();

			this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); //不启用事务，自动签收

			this.producer = this.session.createProducer(null);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage() {
		try {
			Destination destination = this.session.createTopic("topic1");
			TextMessage msg = this.session.createTextMessage("我是内容");
			this.producer.send(destination, msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Publish publish = new Publish();
		publish.sendMessage();
	}
}

