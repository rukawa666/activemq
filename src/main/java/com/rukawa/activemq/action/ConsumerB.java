package com.rukawa.activemq.action;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerB {

	public final String SELECTOR = "receiver = 'B'";

	//1.连接工厂
	private ConnectionFactory connectionFactory;
	//2.连接对象
	private Connection connection;
	//3.Session对象
	private Session session;
	//4.消费者
	private MessageConsumer consumer;
	//5.目标地址
	private Destination destination;


	public ConsumerB() {
		try {
			this.connectionFactory = new ActiveMQConnectionFactory(
					"admin",  //ActiveMQConnectionFactory.DEFAULT_USER,
					"admin",  //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
					"tcp://localhost:61616");

			this.connection = this.connectionFactory.createConnection();
			connection.start();

			this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

			this.destination = this.session.createQueue("first");

			this.consumer = this.session.createConsumer(this.destination, SELECTOR);
			System.out.println("Consumer B Start...");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void receiver() {
		try {
			this.consumer.setMessageListener(new Listener());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	class Listener implements MessageListener {

		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10000);

		ExecutorService executor = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),
				20,
				120L,
				TimeUnit.SECONDS,
				queue);

		@Override
		public void onMessage(Message message) {
			if(message instanceof MapMessage) {
				MapMessage ret = (MapMessage) message;

//				Thread.sleep(500);
//				System.out.println("处理任务：" + ret.getString("id"));

				executor.execute(new MessageTask(ret));

			}
		}
	}


	public static void main(String[] args) {
		ConsumerB b = new ConsumerB();
		b.receiver();
	}


}
