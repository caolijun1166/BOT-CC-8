//****************************************BOT-CC Graduation Desgin***********************************************//

//*******************************************曹礼俊 14111800420**************************************************//
#include <stc12.h>
#include <stdio.h>
#include "delay.h"
//**********************************声明区**********************************//
typedef unsigned char  U8;       /* defined for unsigned 8-bits integer variable 	  无符号8位整型变量  */
#define uchar unsigned char
#define uint unsigned int
#define param 500
//------------------------------------------------------------------------------
void InitUART(void);//串口初始化函数
void InitESP8266(void);//ESP8266初始化函数
void ESP8266SEND(char *a);//ESP8266发送函数
void send(char *a,char num);
void RH();
void COM();
//void Delay_10us();
//void Delay18ms();
//void delay(unsigned int a);
//void delay_ms(unsigned int t);
//------------------------------------------------------------------------------
sbit dht11  = P2^1;
sbit LED = P2^2;
//int count = 0;
U8  U8FLAG,k;
U8  U8comdata;
U8  U8temp;
U8  U8T_data_H,U8T_data_L,U8RH_data_H,U8RH_data_L,U8checkdata;
U8  U8T_data_H_temp,U8T_data_L_temp,U8RH_data_H_temp,U8RH_data_L_temp,U8checkdata_temp;
unsigned char temph[8]={0xee,0xcc,0x02,0x00,0x00,0x00,0x00,0xff};
unsigned char UART_buff;  
//**************************************************************************//

//**********************************主函数**********************************//
void main()
{
	InitUART();   //串口初始化
	InitESP8266();//ESP8266初始化函数
	while(1)
	{
		RH();	
		ESP8266SEND(temph);
   	delay(1000); //延时约1s
	}
}

void ser_int (void) interrupt 4   
{  
    if(RI == 1){  
      RI = 0;
      UART_buff = SBUF;  
      if(UART_buff == '1')  LED = 1;  
      if(UART_buff == '0')  LED = 0;  
    }  
} 


//**********************************串口初始化函数**********************************//
void InitUART(void)//串口初始化函数
{  
   PCON &= 0x7f;  //波特率不倍速
   SCON = 0x50;  //8位数据,可变波特率
   BRT = 0xFD;      //设定独立波特率发生器重装值
   AUXR |= 0x04;  //独立波特率发生器时钟为Fosc,即1T
   AUXR |= 0x01;  //串口1选择独立波特率发生器为波特率发生器
   AUXR |= 0x10;  //启动独立波特率发生器
	 EA = 1;
   
   TR1 = 1;       //启动定时器
   TI = 1;        //打开定时器中断
}

//**********************************ESP8266初始化函数**********************************//
void InitESP8266(void){
	delay_ms(50000);
	//将WIFI模块设置为STA模式
	printf("AT+CWMODE=1\r\n");
	delay_ms(param);
	//将WIFI模块设置为单链接模式
	printf("AT+CIPMUX=0\r\n");
	delay_ms(param);
	//查看STA模式是否设置成功（调试用）
	printf("AT+CWMODE?\r\n");
	delay_ms(param);
	//查看单链接模式是否设置成功（调试用）
	printf("AT+CIPMUX?\r\n");
	delay_ms(param);
	//将WIFI模块设置为透传模式
	printf("AT+CIPMODE=1\r\n");
	delay_ms(param);
	//查看透传模式是否设置成功（调试用）
	printf("AT+CIPMODE?\r\n");
	delay_ms(param);
	//返回当前选择的AP的信息
	printf("AT+CWJAP?\r\n");
	delay_ms(param);
	//连接服务器
	printf("AT+CIPSTART=\"TCP\",\"192.168.155.1\",8888\r\n");
	delay_ms(15000);
	//查看与服务器的连接状态（调试用）
	printf("AT+CIPSTATUS\r\n");
	delay_ms(2000);
	ES = 1;        //打开串口中断，目前必须在这打开串口中断，如果再ESP8266初始化之前打开，那么AT指令发送后通过串口返回的返回值会触发中断，导致一些问题
}

//**********************************ESP8266发送函数**********************************//
void ESP8266SEND(char *a)
{
	unsigned char i;
	//将WIFI模块设置为CIPCEND模式，用来发送数据
	printf("AT+CIPSEND\r\n");
	delay_ms(param);
	//发送数据
	for(i=0;i<8;i++){
		printf("%04X ",a[i]);
	}
	printf("\r\n");
	delay_ms(1000);
	//退出CIPSEND模式
	printf("+++");
	//重启模块，验证CIPSEND模式是否退出成功（调试用）
	//printf("AT+RST\r\n");
}

//**********************************温湿度初始化函数**********************************//
void COM(void)
{
     U8 i;
     for(i=0;i<8;i++)	   
	   {
			 U8FLAG=2;
			 while((!dht11)&&U8FLAG++);
				 Delay_10us();
				 Delay_10us();
				 Delay_10us();
	  	 U8temp=0;
				if(dht11)U8temp=1;
		    U8FLAG=2;
		 while((dht11)&&U8FLAG++);
	   	//超时则跳出for循环		  
	   	 if(U8FLAG==1)break;
	   	//判断数据位是0还是1	 
			 
		// 如果高电平高过预定0高电平值则数据位为 1 
	   	 
		   U8comdata<<=1;
	   	   U8comdata|=U8temp;        //0
	     }//rof	   
}

//**********************************温湿度读取子程序**********************************//
	//--------------------------------
	//----以下变量均为全局变量--------
	//----温度高8位== U8T_data_H------
	//----温度低8位== U8T_data_L------
	//----湿度高8位== U8RH_data_H-----
	//----湿度低8位== U8RH_data_L-----
	//----校验 8位 == U8checkdata-----
	//----调用相关子程序如下----------
	//---- Delay();, Delay_10us();,COM(); 
//***********************************************************************************//
	void RH(void)
	{
	  //主机拉低18ms 
       dht11=0;
	   Delay18ms();
	   dht11=1;
	 //总线由上拉电阻拉高 主机延时20us
	   Delay_10us();
	   Delay_10us();
	 //主机设为输入 判断从机响应信号 
	   dht11=1;
	 //判断从机是否有低电平响应信号 如不响应则跳出，响应则向下运行	  
	   if(!dht11)		 //T !	  
	   {
	   U8FLAG=2;
	 //判断从机是否发出 80us 的低电平响应信号是否结束	 
	   while((!dht11)&&U8FLAG++);
	   U8FLAG=2;
	 //判断从机是否发出 80us 的高电平，如发出则进入数据接收状态
	   while((dht11)&&U8FLAG++);
	 //数据接收状态		 
	   COM();
	   U8RH_data_H_temp=U8comdata;
	   COM();
	   U8RH_data_L_temp=U8comdata;
	   COM();
	   U8T_data_H_temp=U8comdata;
	   COM();
	   U8T_data_L_temp=U8comdata;
	   COM();
	   U8checkdata_temp=U8comdata;
	   dht11=1;
	 //数据校验
	   U8temp=(U8T_data_H_temp+U8T_data_L_temp+U8RH_data_H_temp+U8RH_data_L_temp);
	   if(U8temp==U8checkdata_temp)
	   {
			temph[3]=U8RH_data_H_temp;
			temph[4]=U8RH_data_L_temp;
			temph[5]=U8T_data_H_temp;
			temph[6]=U8T_data_L_temp;
	   }
	   }
	}