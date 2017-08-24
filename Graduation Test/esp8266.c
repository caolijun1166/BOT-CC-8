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