package com.cn.bot.start;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.cn.bot.threadTasks.SensorDataReceiverTask;

@WebListener
public class SensorHandler implements ServletContextListener {
	
	private Logger log = Logger.getLogger(SensorHandler.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		//�����߳��������
		SensorDataReceiverTask sensorDataReceiverTask = new SensorDataReceiverTask(contextEvent.getServletContext());
		//�����̶߳��󣬲����������
		Thread sensorDataReceiverThread = new Thread(sensorDataReceiverTask);
		//�����߳�
		sensorDataReceiverThread.start();
		log.debug("-------------------------------SensorDataReceiverThread has Started in SensorHandler-------------------------------");
	}

}
