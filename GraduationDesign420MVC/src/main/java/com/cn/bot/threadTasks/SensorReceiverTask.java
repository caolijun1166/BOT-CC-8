package com.cn.bot.threadTasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;

public class SensorReceiverTask implements Runnable{
	private ServerSocket server;
	private Socket socket;
	private BufferedReader reader;
	private ServletContext context;
	public SensorReceiverTask(ServletContext context){
		this.context = context;
	}
	//��дrun����
	public void run(){
		try{
			server = new ServerSocket(8888);
			System.out.println("�������׽��ִ����ɹ�");
			System.out.println("�ȴ��ͻ�������...");
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
