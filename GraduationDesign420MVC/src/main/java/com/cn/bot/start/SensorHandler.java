package com.cn.bot.start;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cn.bot.threadTasks.SensorReceiverTask;

public class SensorHandler implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		//创建线程任务对象
		SensorReceiverTask sensorReceiverTask = new SensorReceiverTask(contextEvent.getServletContext());
		//创建线程对象，并将任务放入
		Thread sensorReceiverThread = new Thread(sensorReceiverTask);
		//启动线程
		sensorReceiverThread.start();
	}

}
