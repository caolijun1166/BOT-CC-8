#include "sensor.h"

void delay(unsigned int a) //延时约1ms
{
	unsigned int i;
	while (--a!=0)
	for(i=600;i>0;i--);   //1T单片机i=600，若是12T单片机i=125
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