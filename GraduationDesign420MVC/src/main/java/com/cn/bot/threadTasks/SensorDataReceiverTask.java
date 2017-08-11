package com.cn.bot.threadTasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class SensorDataReceiverTask implements Runnable{
	private ServerSocket server;
	private Socket socket;
	private BufferedReader reader;
	private ServletContext context;//����ServletContext���󣬽�������������
	private Logger log = Logger.getLogger(SensorDataReceiverTask.class);
	
	public SensorDataReceiverTask(ServletContext context){
		this.context = context;
	}
	
	public void run(){
		try{
			server = new ServerSocket(8888);
			log.debug("-------------------------------�������׽��ִ����ɹ�-------------------------------");
			log.debug("-------------------------------�ȴ��ͻ�������-------------------------------");
			while(true){
				socket = server.accept();
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//System.out.println("�ͻ�����"+reader.readLine());
				context.setAttribute("humiTemp", reader.readLine());
				System.out.println("�������������óɹ���");
				if(reader != null)
					reader.close();
				if(socket != null){
					socket.close();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
