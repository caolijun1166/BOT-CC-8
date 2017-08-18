//****************************************BOT-CC Graduation Desgin***********************************************//

//*******************************************���� 14111800420**************************************************//
#include <stc12.h>
#include <stdio.h>
#include <intrins.h>
//**********************************������**********************************//
typedef unsigned char  U8;       /* defined for unsigned 8-bits integer variable 	  �޷���8λ���ͱ���  */
#define uchar unsigned char
#define uint unsigned int
#define param 500
//------------------------------------------------------------------------------
void InitUART(void);//���ڳ�ʼ������
void InitESP8266(void);//ESP8266��ʼ������
void ESP8266SEND(char *a);//ESP8266���ͺ���
void delay(unsigned int a);
void delay_ms(unsigned int t);
void send(char *a,char num);
void Delay_10us();
void Delay18ms();
void RH();
void COM();
//------------------------------------------------------------------------------
sbit  dht11  = P2^1;
U8  U8FLAG,k;
U8  U8comdata;
U8  U8temp;
U8  U8T_data_H,U8T_data_L,U8RH_data_H,U8RH_data_L,U8checkdata;
U8  U8T_data_H_temp,U8T_data_L_temp,U8RH_data_H_temp,U8RH_data_L_temp,U8checkdata_temp;
unsigned char temph[8]={0xee,0xcc,0x02,0x00,0x00,0x00,0x00,0xff};
//**************************************************************************//

//**********************************������**********************************//
void main()
{
	InitUART();   //���ڳ�ʼ��
	InitESP8266();//ESP8266��ʼ������
	while(1)
	{
		RH();	
		ESP8266SEND(temph);
   	delay(1000); //��ʱԼ1s
	}
}


//**********************************���ڳ�ʼ������**********************************//
void InitUART(void)//���ڳ�ʼ������
{  
   PCON &= 0x7f;  //�����ʲ�����
   SCON = 0x50;  //8λ����,�ɱ䲨����
   BRT = 0xFD;      //�趨���������ʷ�������װֵ
   AUXR |= 0x04;  //���������ʷ�����ʱ��ΪFosc,��1T
   AUXR |= 0x01;  //����1ѡ����������ʷ�����Ϊ�����ʷ�����
   AUXR |= 0x10;  //�������������ʷ�����
   ES = 1;        //�򿪴����ж�
   TR1 = 1;       //������ʱ��
   TI = 1;        //�򿪶�ʱ���ж�
}
//**********************************ESP8266��ʼ������**********************************//
void InitESP8266(void){
	delay_ms(50000);
	//��WIFIģ������ΪSTAģʽ
	printf("AT+CWMODE=1\r\n");
	delay_ms(param);
	//��WIFIģ������Ϊ������ģʽ
	printf("AT+CIPMUX=0\r\n");
	delay_ms(param);
	//�鿴STAģʽ�Ƿ����óɹ��������ã�
	printf("AT+CWMODE?\r\n");
	delay_ms(param);
	//�鿴������ģʽ�Ƿ����óɹ��������ã�
	printf("AT+CIPMUX?\r\n");
	delay_ms(param);
	//��WIFIģ������Ϊ͸��ģʽ
	printf("AT+CIPMODE=1\r\n");
	delay_ms(param);
	//�鿴͸��ģʽ�Ƿ����óɹ��������ã�
	printf("AT+CIPMODE?\r\n");
	delay_ms(param);
	//���ص�ǰѡ���AP����Ϣ
	printf("AT+CWJAP?\r\n");
	delay_ms(param);
	//���ӷ�����
	printf("AT+CIPSTART=\"TCP\",\"192.168.155.1\",8888\r\n");
	delay_ms(15000);
	//�鿴�������������״̬�������ã�
	printf("AT+CIPSTATUS\r\n");
}
//**********************************ESP8266���ͺ���**********************************//
void ESP8266SEND(char *a)
{
	unsigned char i;
	//��WIFIģ������ΪCIPCENDģʽ��������������
	printf("AT+CIPSEND\r\n");
	delay_ms(param);
	//��������
	for(i=0;i<8;i++){
		printf("%04X ",a[i]);
	}
	printf("\r\n");
	delay_ms(1000);
	//�˳�CIPSENDģʽ
	printf("+++");
	//����ģ�飬��֤CIPSENDģʽ�Ƿ��˳��ɹ��������ã�
	//printf("AT+RST\r\n");
}
//**********************************��ʪ�ȳ�ʼ������**********************************//
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
	   	//��ʱ������forѭ��		  
	   	 if(U8FLAG==1)break;
	   	//�ж�����λ��0����1	 
			 
		// ����ߵ�ƽ�߹�Ԥ��0�ߵ�ƽֵ������λΪ 1 
	   	 
		   U8comdata<<=1;
	   	   U8comdata|=U8temp;        //0
	     }//rof	   
}

//**********************************��ʪ�ȶ�ȡ�ӳ���**********************************//
	//--------------------------------
	//----���±�����Ϊȫ�ֱ���--------
	//----�¶ȸ�8λ== U8T_data_H------
	//----�¶ȵ�8λ== U8T_data_L------
	//----ʪ�ȸ�8λ== U8RH_data_H-----
	//----ʪ�ȵ�8λ== U8RH_data_L-----
	//----У�� 8λ == U8checkdata-----
	//----��������ӳ�������----------
	//---- Delay();, Delay_10us();,COM(); 
//***********************************************************************************//
	void RH(void)
	{
	  //��������18ms 
       dht11=0;
	   Delay18ms();
	   dht11=1;
	 //������������������ ������ʱ20us
	   Delay_10us();
	   Delay_10us();
	 //������Ϊ���� �жϴӻ���Ӧ�ź� 
	   dht11=1;
	 //�жϴӻ��Ƿ��е͵�ƽ��Ӧ�ź� �粻��Ӧ����������Ӧ����������	  
	   if(!dht11)		 //T !	  
	   {
	   U8FLAG=2;
	 //�жϴӻ��Ƿ񷢳� 80us �ĵ͵�ƽ��Ӧ�ź��Ƿ����	 
	   while((!dht11)&&U8FLAG++);
	   U8FLAG=2;
	 //�жϴӻ��Ƿ񷢳� 80us �ĸߵ�ƽ���緢����������ݽ���״̬
	   while((dht11)&&U8FLAG++);
	 //���ݽ���״̬		 
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
	 //����У��
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

//**********************************��ʱ����ϵ��**********************************//
void delay(unsigned int a) //��ʱԼ1ms
{
	unsigned int i;
	while (--a!=0)
	for(i=600;i>0;i--);   //1T��Ƭ��i=600������12T��Ƭ��i=125
}
//------------------------------------------------------------------------------
void Delay_10us()		//@11.0592MHz
{
	unsigned char i;

	_nop_();
	_nop_();
	_nop_();
	i = 24;
	while (--i);
}

void Delay18ms()		//@11.0592MHz
{
	unsigned char i, j, k;

	_nop_();
	_nop_();
	i = 1;
	j = 194;
	k = 159;
	do
	{
		do
		{
			while (--k);
		} while (--j);
	} while (--i);
}
//------------------------------------------------------------------------------
void delay_ms(unsigned int t)  
{  
    unsigned char a,b;  
    while(t--)  
    {  
      for(b=102;b>0;b--)  
      for(a=3;a>0;a--);  
    }  
}
//********************************************************************************//