package com.cn.bot.threadTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.cn.bot.util.MemcacheHelper;
import com.cn.bot.util.TimeHelper;

public class SensorDataReceiverTask implements Runnable{
	private ServerSocket server;
	private Socket socket;
	private BufferedReader reader;
	private ServletContext context;//����ServletContext���󣬽�������������
	private Logger log = Logger.getLogger(SensorDataReceiverTask.class);
	public SensorDataReceiverTask(ServletContext context){
		this.context = context;
		//����ָ������˿�
		try {
			server = new ServerSocket(8888);
			log.debug("-------------------------------�������׽��ִ����ɹ� !-------------------------------");
			log.debug("-------------------------------�ȴ��ͻ������� !-------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��������⣬���������Ͽ����Ӻ�ʵʱ��ʪ����ʾ���һ�λ�ȡ�������ݣ����������������
	public void run(){
		while(true){
			//�����˿�
			try{
				socket = server.accept();//ִ�е��˴�������ǰ�߳�
				//��ȡ����
				while(true){
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String data = reader.readLine();//readLine()��read()���������ķ���
					socket.setSoTimeout(20000);//���ö�ȡ���ݳ�ʱʱ��,���20����δ�������ݣ�����׳�SocketTimeoutException�쳣
					log.debug("-------------------------------�ͻ�����" + data + "-------------------------------");
					
					//������ӵ�ServletContext�У�����ȡʵʱ����ҳ����
					context.setAttribute("humiTemp", data);
					log.debug("-------------------------------�������������óɹ���-------------------------------");
					
					//�����ݻ�����Memcache��
					MemcacheHelper mcc = MemcacheHelper.getInstance();
					String key = TimeHelper.getCurrentTime(false);
					log.debug("-------------------------------dateKey is: " + key + "-------------------------------");
					
					boolean isSuccess = mcc.add(key, data, TimeHelper.setExpire(1));
					if(isSuccess)
						log.debug("-------------------------------Mcc Add Success !-------------------------------\r");
					else
						log.debug("-------------------------------Mcc Add Fail !-------------------------------\r");
				}
			}catch(SocketTimeoutException e){
				e.printStackTrace();
				log.debug("-------------------------------Get Sensor Data Time Out, The Sensor May Lost its Connection !-------------------------------");
				//����������������
				context.setAttribute("humiTemp", null);
				//�ر������Դ
				try{
					if(reader != null)
						reader.close();
					if(socket != null){
						socket.close();
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}