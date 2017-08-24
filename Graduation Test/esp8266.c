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