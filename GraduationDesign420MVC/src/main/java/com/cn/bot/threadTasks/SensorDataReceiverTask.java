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
	private ServletContext context;//����ServletContext���󣬽�������������
	private Logger log = Logger.getLogger(SensorDataReceiverTask.class);
	private JSONObject obj;
	private int dataAmount = 0;
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
				obj = new JSONObject();
				socket = server.accept();//ִ�е��˴�������ǰ�̣߳��ȴ�����������
				//��ȡ����
				while(true){
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String data = reader.readLine();//readLine()��read()���������ķ���
					socket.setSoTimeout(20000);//���ö�ȡ���ݳ�ʱʱ��,���20����δ�������ݣ�����׳�SocketTimeoutException�쳣
					log.debug("-------------------------------�ͻ�����" + data + "-------------------------------");
					
					//������ӵ�ServletContext�У�����ȡʵʱ����ҳ����
					context.setAttribute("humiTemp", data);
					log.debug("-------------------------------�������������óɹ���-------------------------------");
					
					//��ȡ��ǰʱ�䣬��ΪJSONObject��key
					String key = TimeHelper.getCurrentTime(false);
					log.debug("-------------------------------dateKey is: " + key + "-------------------------------");
					//�����������ݷ�װ��JSONObject��
					obj.put(key, data);
					dataAmount++;
					
					//��JSONObject�з�װ��10�����ݺ�д��Mecmcahce��
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
					//�����쳣��������ѭ��
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
				//����catch SocketTimeoutException����ִ����ϣ���û�б���쳣���򷵻��������ѭ�����ٴεȴ�����������
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}