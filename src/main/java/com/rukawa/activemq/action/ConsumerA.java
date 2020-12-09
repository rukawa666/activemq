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

public class ConsumerA {

	public final String SELECTOR = "receiver = 'A'";

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


	public ConsumerA() {
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
			System.out.println("Consumer A Start...");
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
		/**
		 * BlockingQueue:支持两个附加操作的Queue，这两个操作是：检索元素时等待队列变为非空，以及存储元素时等待空间变得可用。
		 * 不接受null元素。试图add，put或者offer一个null元素时，某些实现会抛出NullPointerException被用作指示poll操作失败额警戒值。
		 * 可以限定容量。它在任意给定时间都可以有一个remainingCapacity，超出此容量，便无法无阻塞的put额外的元素。没有任何内部容量约束的BlockingQueue
		 * 总是报告Integer.MAX_VALUE的剩余容量。
		 * 实现主要用于生产者-消费者队列，但它还支持Collection接口。
		 * 实现是线程安全的。所有排队方法都可以使用内部锁定或其他形式的并发控制来自动达到他们的目的。
		 */

		/**
		 * ArrayBlockingQueue:一个由数组支持的有界阻塞队列。此队列按先进先出(FIFO)原则对元素进行排序。队列的头部是队列中存在时间最长的元素。队列的尾部
		 * 是队列中存在时间最短的元素。新元素插入到队列的尾部，队列检索操作则是从队列头部开始获得元素。
		 *
		 * 一个典型的有界缓冲区，固定大小的数组在其中保持生产者插入的元素和使用者提取的元素。一旦创建了这样的缓冲区，就不再增加其容量。试图向已满队列中存放元素
		 * 会导致放入操作受阻塞；试图从空队列中检索元素将导致类似阻塞。
		 *
		 * 此类支持对等待的生产者线程和使用者线程进行排序的可选公平策略。默认情况下，不保证这种排序。然而，通过将公平性（fairness）设置为true而构造的队列
		 * 允许按照FIFO顺序访问线程。公平性通常会降低吞吐量，但是减少了可变性和避免了"不平衡性".
		 */

		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10000);
//		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10000, true);

		/**
		 * Executor提供了管理终止的办法，以及可为跟踪一个或多个异步任务执行状况而生成Future的办法。可以关闭ExecutorService，
		 * 这将导致其停止接收新任务。关闭后，执行程序将最后终止，这时没有任务在执行，也没有任务在等待执行，并且无法提交新任务。
		 *
		 * 通过创建并返回一个可用于取消执行和/或等待完成的Future，方法submit扩展了基本方法。方法invokeAny和invokeAll是批量执行的最常用形式，他们执行
		 * 任务集合，然后等待至少一个，或全部任务完成。
		 */

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
		ConsumerA a = new ConsumerA();
		a.receiver();
	}


}
