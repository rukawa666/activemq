package com.rukawa.activemq.helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver {
	public static void main(String[] args) {
		//1.建立ConnectionFactory工厂对象，需要填入用户名，密码，以及要连接的地址，均使用默认即可，默认端口为"tcp:localhost:61616"
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
//				ActiveMQConnectionFactory.DEFAULT_USER,
//				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"admin",
				"admin",
				"tcp://localhost:61616");

		//2.通过工厂对象创建一个Connection连接，并且调用Connection的start方法开启连接，Connection默认是关闭的
		try {
			Connection connection = connectionFactory.createConnection();
			connection.start();

			//3.通过Connection创建Session会话(上下文环境对象)，用于接收消息，参数配置1表示是否启用事务，参数配置2表示签收模式，一般我们设置为自动签收
			Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

			//4.通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象，在PTP模式中，Destination被称为Queue即队列，
			//在Pub/Sub模式，Destination被称为Topic即主题。在程序中可以使用多个Queue和Topic。
			Destination destination = session.createQueue("first");

			//5.通过Session创建MessageConsumer
			MessageConsumer consumer = session.createConsumer(destination);

			while(true) {
				TextMessage message = (TextMessage) consumer.receive();
				//如果是客户端签收，则需此处签收
				//message.acknowledge();

				System.out.println("消费数据：" + message.getText());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
