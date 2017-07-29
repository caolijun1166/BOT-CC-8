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
	//重写run函数
	public void run(){
		try{
			server = new ServerSocket(8888);
			System.out.println("服务器套接字创建成功");
			System.out.println("等待客户机连接...");
			while(true){
				socket = server.accept();
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//System.out.println("客户机："+reader.readLine());
				context.setAttribute("humiTemp", reader.readLine());
				System.out.println("上下文属性设置成功！");
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
