package com.rukawa.activemq.action;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	//1.连接工厂
	private ConnectionFactory connectionFactory;
	//2.连接对象
	private Connection connection;
	//3.Session对象
	private Session session;
	//4.生产者
	private MessageProducer producer;

	public Producer() {
		try {
			this.connectionFactory = new ActiveMQConnectionFactory(
					"admin",  //ActiveMQConnectionFactory.DEFAULT_USER,
					"admin",  //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
					"tcp://localhost:61616");

			this.connection = this.connectionFactory.createConnection();
			connection.start();

			this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

			this.producer = this.session.createProducer(null);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public Session getSession() {
		return this.session;
	}

	public void send() {
		try {
			Destination destination = this.session.createQueue("first");
			for(int i = 0; i < 50; i++) {
				MapMessage msg = this.session.createMapMessage();
				int id = i;
				msg.setInt("ID", id);
				msg.setString("name", "Jordan" + i);
				msg.setString("age", "" + i);
				String receiver = id%2 == 0 ? "A" : "B";
				msg.setStringProperty("receiver", receiver);
				this.producer.send(destination, msg, DeliveryMode.NON_PERSISTENT, 2, 1000*60*10L);
				System.out.println("msg send id : " + id);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Producer producer = new Producer();
		producer.send();
	}

}

