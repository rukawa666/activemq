package com.rukawa.activemq.p2p;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	//单例模式
	//1 连接工厂
	private ConnectionFactory connectionFactory;
	//2 连接对象
	private Connection connection;
	//3 Session对象
	private Session session;
	//4 生产者
	private MessageProducer producer;

	public Producer() {

		try {
			this.connectionFactory = new ActiveMQConnectionFactory(
					"admin",
					"admin",
					"tcp://localhost:61616");

			this.connection = connectionFactory.createConnection();
			this.connection.start();

			this.session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

			this.producer = session.createProducer(null);

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
			MapMessage msg1 = this.session.createMapMessage();
			msg1.setString("name", "Jordan");
			msg1.setString("age", "23");
			msg1.setStringProperty("color", "blue");
			msg1.setIntProperty("sal", 2200);

			MapMessage msg2 = this.session.createMapMessage();
			msg2.setString("name", "James");
			msg2.setString("age", "24");
			msg2.setStringProperty("color", "red");
			msg2.setIntProperty("sal", 1300);

			MapMessage msg3 = this.session.createMapMessage();
			msg3.setString("name", "Wade");
			msg3.setString("age", "25");
			msg3.setStringProperty("color", "green");
			msg3.setIntProperty("sal", 1500);

			MapMessage msg4 = this.session.createMapMessage();
			msg4.setString("name", "Kobe");
			msg4.setString("age", "26");
			msg4.setStringProperty("color", "blue");
			msg4.setIntProperty("sal", 2000);
			// 当ttl到期，进入死信队列，保证一些有时效性的这种消息
			producer.setTimeToLive(1000);
			this.producer.send(destination, msg1, DeliveryMode.NON_PERSISTENT, 2, 1000*60*10L);
			this.producer.send(destination, msg2, DeliveryMode.NON_PERSISTENT, 3, 1000*60*10L);
			this.producer.send(destination, msg3, DeliveryMode.NON_PERSISTENT, 6, 1000*60*10L);
			this.producer.send(destination, msg4, DeliveryMode.NON_PERSISTENT, 9, 1000*60*10L);

			BytesMessage bytesMessage = session.createBytesMessage();
			bytesMessage.writeUTF("hi~!");
			producer.send(destination, bytesMessage);
			if(null != session) {
				session.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void send2() {
		try {
			Destination destination = this.session.createQueue("first");
			TextMessage textMessage = this.session.createTextMessage("我是一个字符串内容");
			this.producer.send(destination, textMessage, DeliveryMode.NON_PERSISTENT, 9, 1000*60*10L);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Producer producer = new Producer();
		producer.send();
//		producer.send2();
	}

}

