package com.rukawa.activemq.helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class Sender {
	public static void main(String[] args) {
		//第一步：建立CollectionFactory工厂对象，需要填入用户名，密码，以及要连接的地址，均默认地址即可
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
//				ActiveMQConnectionFactory.DEFAULT_USER,
//				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"admin",
				"admin",
				"tcp://localhost:61616");

		//第二步：通过ConnectionFactory工厂对象我们创建一个Connection连接，并且调用Connection的start方法开启连接，Connection默认是关闭的
		try {
			Connection connection = connectionFactory.createConnection();
			connection.start();

//			connectionFactory.createConnection("admin", "admin");

			//第三步：通过Connection对象创建Session会话(上下文环境对象)，用于接收消息，参数配置1是否启用该事务，参数配置2为签收模式，一般我们设置自动签收
			/**
			 * 签收方式：
			 *  1.Session.AUTO_ACKNOWLEDGE当客户端从receive或Message成功返回时，Session自动签收客户端的这条消息的收条。
			 *  2.Session.CLIENT_ACKNOWLEDGE当客户端通过调用消息的acknowledge方法签收消息。在这种情况下，签收发生在Session层面：
			 *    签收一个已消费的消息会自动的签收这个Session所有已消费消息的收条。
			 *  3.Session.DUPS_OK_ACKNOWLEDGE此选项指示Session不必确保对传送消息的签收。它可能引起消息的重复，
			 *    但是降低了Session的开销，所以只有客户端能够容忍重复的消息，才可以使用。
			 */
			Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

			//第四步：通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象，
			//在PTP模式中，Destination被称为Queue，即队列；在Pub/Sub模式，Destination被称为Topic即主题。在程序中可以使用多个Queue和Topic。
			Destination destination = session.createQueue("first");

			//第五步：我们需要通过Session对象创建消息的发送和接收对象MessageProducer/MessageConsumer
			MessageProducer producer = session.createProducer(null);

			//第六步：我们可以通过MessageProducer的setDeliveryMode方法为其设置持久化特性和非持久化特性(DeliveryMode)
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			//第七步：最后我们使用JMS规范的TextMessage形式创建数据(通过Session对象)，并用MessageProducer的send方法发送数据。同理客户端使用receive
			//方法进行接收数据。最后不要忘记关闭Connection连接
			for(int i = 0; i < 9; i++) {
				TextMessage msg = session.createTextMessage("我是消息内容" + i);
//				producer.send(destination, msg);
				//第一个参数为目标地址，第二个参数为具体的数据信息，第三个参数为传送数据的模式，第四个参数为传送的优先级，第五个参数为消息的过期时间
				producer.send(destination, msg, DeliveryMode.PERSISTENT, i, 5000);
				TimeUnit.SECONDS.sleep(1);
			}
			//如果启用事务，需要事务提交
			//session.commit();

			if(connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

