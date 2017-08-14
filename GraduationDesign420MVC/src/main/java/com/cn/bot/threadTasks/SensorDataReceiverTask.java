package com.cn.bot.threadTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.cn.bot.util.MemcacheHelper;
import com.cn.bot.util.TimeHelper;

public class SensorDataReceiverTask implements Runnable{
	private ServerSocket server;
	private Socket socket;
	private BufferedReader reader;
	private ServletContext context;//创建ServletContext对象，将数据设入其中
	private Logger log = Logger.getLogger(SensorDataReceiverTask.class);
	private JSONObject obj;
	private int dataAmount = 0;
	public SensorDataReceiverTask(ServletContext context){
		this.context = context;
		//创建指定服务端口
		try {
			server = new ServerSocket(8888);
			log.debug("-------------------------------服务器套接字创建成功 !-------------------------------");
			log.debug("-------------------------------等待客户机连接 !-------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//待解决问题，当传感器断开连接后，实时温湿度显示最后一次获取到的数据，而并非清零的问题
	public void run(){
		while(true){
			//监听端口
			try{
				obj = new JSONObject();
				socket = server.accept();//执行到此处阻塞当前线程，等待传感器连接
				//读取数据
				while(true){
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String data = reader.readLine();//readLine()、read()都是阻塞的方法
					socket.setSoTimeout(20000);//设置读取数据超时时间,如果20秒内未读到数据，则会抛出SocketTimeoutException异常
					log.debug("-------------------------------客户机：" + data + "-------------------------------");
					
					//数据添加到ServletContext中，供获取实时数据页面用
					context.setAttribute("humiTemp", data);
					log.debug("-------------------------------上下文属性设置成功！-------------------------------");
					
					//获取当前时间，作为JSONObject的key
					String key = TimeHelper.getCurrentTime(false);
					log.debug("-------------------------------dateKey is: " + key + "-------------------------------");
					//将传感器数据封装至JSONObject中
					obj.put(key, data);
					dataAmount++;
					
					//当JSONObject中封装了10个数据后，写入Mecmcahce中
					if(dataAmount == 10){
						log.debug("-------------------------------Enter Mcc Operation !-------------------------------\r");
						MemcacheHelper mcc = MemcacheHelper.getInstance();
						boolean isSuccess = mcc.set("SensorData", obj, TimeHelper.setExpire(1));
						if(isSuccess){
							log.debug("-------------------------------Mcc Add Success !-------------------------------\r");
							JSONObject dataMapResult = JSONObject.parseObject((String)mcc.get("SensorData"));
							for(String sensorDataKey: dataMapResult.keySet()){
								String singleSensorData = dataMapResult.getString(sensorDataKey);
								log.debug("-------------------------------Sensor Data in" + sensorDataKey + "is: " + singleSensorData + "-------------------------------\r");
							}
						}
						else
							log.debug("-------------------------------Mcc Add Fail !-------------------------------\r");
						dataAmount = 0;
					}else
						log.debug("-------------------------------Current Data Amount is:" + dataAmount +" -------------------------------\r");
					//如有异常则跳出该循环
				}
			}catch(SocketTimeoutException e){
				e.printStackTrace();
				log.debug("-------------------------------Get Sensor Data Time Out, The Sensor May Lost its Connection !-------------------------------");
				//将传感器数据置零
				context.setAttribute("humiTemp", null);
				//关闭相关资源
				try{
					if(reader != null)
						reader.close();
					if(socket != null){
						socket.close();
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
				//到此catch SocketTimeoutException语句块执行完毕，如没有别的异常，则返回至最外层循环，再次等待传感器连接
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}