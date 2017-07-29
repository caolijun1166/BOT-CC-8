package com.cn.bot.start;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.cn.bot.threadTasks.SensorReceiverTask;

@WebListener
public class SensorHandler implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		//�����߳��������
		SensorReceiverTask sensorReceiverTask = new SensorReceiverTask(contextEvent.getServletContext());
		//�����̶߳��󣬲����������
		Thread sensorReceiverThread = new Thread(sensorReceiverTask);
		//�����߳�
		sensorReceiverThread.start();
		System.out.println("-------------------------------------------------------Start-------------------------------------------------------");
	}

}
