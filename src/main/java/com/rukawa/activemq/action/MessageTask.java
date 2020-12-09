package com.rukawa.activemq.action;

import javax.jms.JMSException;
import javax.jms.MapMessage;

public class MessageTask implements Runnable {
	private MapMessage mapMessage;

	public MessageTask(MapMessage mapMessage) {
		this.mapMessage = mapMessage;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "处理任务：" + this.mapMessage.getString("ID"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}



}
