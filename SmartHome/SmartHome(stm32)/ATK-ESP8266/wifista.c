#include "common.h"
#include "stdlib.h"
#include "ds18b20.h"
#include "rc522_function.h"
#include "rc522_config.h"
#include "string.h"
#include "led.h"
#include "beep.h"
/////////////////////////////////////////////////////////////////////////////////////////////////////////// 
//������ֻ��ѧϰʹ�ã�δ��������ɣ��������������κ���;
//ALIENTEK STM32������
//ATK-ESP8266 WIFIģ�� WIFI STA��������	   
//����ԭ��@ALIENTEK
//������̳:www.openedv.com
//�޸�����:2015/4/3
//�汾��V1.0
//��Ȩ���У�����ؾ���
//Copyright(C) ������������ӿƼ����޹�˾ 2009-2019
//All rights reserved									  
/////////////////////////////////////////////////////////////////////////////////////////////////////////// 

//ATK-ESP8266 WIFI STA����
//���ڲ���TCP/UDP����
//����ֵ:0,����
//    ����,�������
u8 netpro=0;	//����ģʽ
u8 atk_8266_wifista_test(void)
{ u8 cnt=0;
	//u8 netpro=0;	//����ģʽ
	u8 i ;
	short temp=0 ;
	char *a ;
	char *shake="shake";
  char cStr[30] ;
  u8 flag = 0;
	u8 flag1 = 0;
  u8 ucArray_ID [ 4 ];                                                                                             //????IC?????UID(IC????)
	u8 ucStatusReturn;   
	u8 timex=0; 
	u8 ipbuf[16]; 	//IP����
	u8 *p;
	u16 t=999;		//���ٵ�һ�λ�ȡ����״̬
	u8 res=0;
	u16 rlen=0;
	u8 constate=0;	//����״̬
	
	p=mymalloc(SRAMIN,32);							//����32�ֽ��ڴ�
	atk_8266_send_cmd("AT+CWMODE=1","OK",50);		//����WIFI STAģʽ
	atk_8266_send_cmd("AT+RST","OK",20);		//DHCP�������ر�(��APģʽ��Ч) 
	delay_ms(1000);         //��ʱ3S�ȴ������ɹ�
	delay_ms(1000);
	delay_ms(1000);
	delay_ms(1000);
	//�������ӵ���WIFI��������/���ܷ�ʽ/����,�⼸��������Ҫ�������Լ���·�������ý����޸�!! 
	sprintf((char*)p,"AT+CWJAP=\"%s\",\"%s\"",wifista_ssid,wifista_password);//�������߲���:ssid,����
	while(atk_8266_send_cmd(p,"WIFI GOT IP",300));					//����Ŀ��·����,���һ��IP

	netpro|=atk_8266_netpro_sel(50,30,(u8*)ATK_ESP8266_CWMODE_TBL[0]);	//ѡ������ģʽ

	     //TCP
				LCD_Clear(WHITE);
				POINT_COLOR=RED;
				Show_Str_Mid(0,30,"ATK-ESP WIFI-STA ����",16,240); 
				Show_Str(30,50,200,16,"��������ATK-ESPģ��,���Ե�...",12,0);
				atk_8266_send_cmd("AT+CIPMUX=1","OK",20);   //0�������ӣ�1��������
				sprintf((char*)p,"AT+CIPSERVER=1,%s",(u8*)portnum);    //����Serverģʽ(0���رգ�1����)���˿ں�Ϊportnum
				atk_8266_send_cmd(p,"OK",50);   


		  LCD_Clear(WHITE);
			POINT_COLOR=RED;
			Show_Str_Mid(0,30,"ATK-ESP WIFI-STA ����",16,240);
			Show_Str(30,50,200,16,"��������ATK-ESPģ��,���Ե�...",12,0);			
			LCD_Fill(30,50,239,50+12,WHITE);			//���֮ǰ����ʾ
			Show_Str(30,50,200,16,"WK_UP:�˳�����  KEY0:��������",12,0);
			LCD_Fill(30,80,239,80+12,WHITE);
			atk_8266_get_wanip(ipbuf);//������ģʽ,��ȡWAN IP
			sprintf((char*)p,"IP��ַ:%s �˿�:%s",ipbuf,(u8*)portnum);
			Show_Str(30,65,200,12,p,12,0);				//��ʾIP��ַ�Ͷ˿�	
			Show_Str(30,80,200,12,"״̬:",12,0); 		//����״̬
			Show_Str(120,80,200,12,"ģʽ:",12,0); 		//����״̬
			Show_Str(30,100,200,12,"��������:",12,0); 	//��������
			Show_Str(30,115,200,12,"��������:",12,0);	//��������
			atk_8266_wificonf_show(30,180,"������·�������߲���Ϊ:",(u8*)wifista_ssid,(u8*)wifista_encryption,(u8*)wifista_password);
			POINT_COLOR=BLUE;
			Show_Str(120+30,80,200,12,(u8*)ATK_ESP8266_WORKMODE_TBL[netpro],12,0); 		//����״̬
			USART3_RX_STA=0;
			while(1)
			{
			       cnt ++  ;
			      temp=DS18B20_Get_Temp();
			    	if(SHAKE==0)
				   {
				    atk_8266_send_cmd("AT+CIPSEND=0,5","OK",200);  //����ָ�����ȵ�����
					 
						atk_8266_send_data(shake,"OK",100);  //����ָ�����ȵ�����
				   }
					  if((temp/10)>=30)
            {
						    BEEP=1 ;
						}
						else
						{
						   BEEP= 0;
						}
				    sprintf((char*)p,"t:%02d.%dC",temp/10,temp%10);//��������
				    // sprintf((char*)p,"1111111111111111111\n");
						Show_Str(30+54,100,200,12,p,12,0);
						atk_8266_send_cmd("AT+CIPSEND=0,7","OK",200);  //����ָ�����ȵ�����
					 
						atk_8266_send_data(p,"OK",100);  //����ָ�����ȵ�����
						timex=100;
					
				if(SHAKE==0)
				{
				    sprintf((char*)p,"t:%02d.%dC",temp/10,temp%10);
				}
	   
				 ucStatusReturn = PcdRequest ( PICC_REQALL, ucArray_ID );	
				 if ( ucStatusReturn == MI_OK  )
		{
			if ( PcdAnticoll ( ucArray_ID ) == MI_OK )                                                                   //???(???????????????,?????????????????)
			{  
				flag1=1;

				sprintf ( cStr,"ID:%02d%02d%02d%02d%s" ,ucArray_ID [ 0 ], ucArray_ID [ 1 ], ucArray_ID [ 2 ], ucArray_ID [ 3 ],p );
			 // 	Show_Str(10,200,200,12,cStr,12,0);
				LCD_ShowString ( 0,42*16,200,16,16,cStr);
				atk_8266_send_cmd("AT+CIPSEND=0,20","OK",200);  //����ָ�����ȵ�����
			//	delay_ms(200);
				atk_8266_send_data((u8 *)cStr,"OK",100);  //����ָ�����ȵ�����
			//	delay_ms(1000);
				}
	  	}
				
				if(flag)  
				{ 
					GPIO_SetBits(GPIOF,GPIO_Pin_11) ;

					 for(i=0;i<10;i++)
				  { 
					  BEEP=!BEEP ;
				    delay_ms(100) ;
					}
					flag = 0 ;
					BEEP = 0;
					cnt = 0;
		 //   GPIO_SetBits(GPIOF,GPIO_Pin_11) ;
				}
				else if (cnt==10)
				{
				   GPIO_ResetBits(GPIOF,GPIO_Pin_11) ;
				}
					
			
				if(timex)timex--;
				if(timex==1)LCD_Fill(30+54,100,239,112,WHITE);
				t++;
			
	delay_ms(1000) ;
			   
				if(USART3_RX_STA&0X8000)		//���յ�һ��������
				{  
					a=(char *)(USART3_RX_BUF);
//					printf("///////// %s",USART3_RX_BUF);	//���͵����
//					printf("*********    %s\n\n",a);
   			
			 	if(match_string(a,"open")== 0)
					{
					   LED0=!LED0 ;
						 
					}
					else{
						LED1=!LED1 ;
					   flag = 1;
					}
					

			    rlen=USART3_RX_STA&0X7FFF;	//�õ����ν��յ������ݳ���
					USART3_RX_BUF[rlen]=0;		//��ӽ����� 
			//		printf("%s",USART3_RX_BUF);	//���͵�����   
					sprintf((char*)p,"�յ�%d�ֽ�,��������",rlen);//���յ����ֽ��� 
					LCD_Fill(30+54,115,239,130,WHITE);
					POINT_COLOR=BRED;
					Show_Str(30+54,115,156,12,p,12,0); 			//��ʾ���յ������ݳ���
					POINT_COLOR=BLUE;
					LCD_Fill(30,130,239,319,WHITE);
        
					Show_Str(30,130,180,190,USART3_RX_BUF,12,0);//��ʾ���յ�������  
					
					USART3_RX_STA=0;
					if(constate!='+')t=1000;		//״̬Ϊ��δ����,������������״̬
					else t=0;                   //״̬Ϊ�Ѿ�������,10����ټ��
				}  
				if(t==1000)//����10����û���յ��κ�����,��������ǲ��ǻ�����.
				{
//			//		LCD_Fill(30+54,125,239,130,WHITE);
//					LCD_Fill(60,80,120,92,WHITE);
					constate=atk_8266_consta_check();//�õ�����״̬
					if(constate=='+')Show_Str(30+30,80,200,12,"���ӳɹ�",12,0);  //����״̬
					else Show_Str(30+30,80,200,12,"����ʧ��",12,0); 	 
					t=0;
				}
				
				if((t%20)==0);
				atk_8266_at_response(1);

			}
	myfree(SRAMIN,p);		//�ͷ��ڴ� 
	return res;		
} 



















int match_string( char *ptr, char *mh)
{
        int count=0;
        char *x;
	      char *y;
        int len;
        len=strlen(mh);
        if(ptr==NULL)
                return 0;
        for(;*ptr!='\0';ptr++)
        {
                if(*ptr==*mh)
                {
                        x=ptr;
                        y=mh;
                        count=0;
                        while(*x!='\0'&&*y!='\0')
                        {
                                while((*x++)==(*y++))
                                {
                                        count++;
                                     
                                        if(count==len)
                                                return 1;
                                }
                        }
        }
        }
        return 0;

}



//int Sunday(const char *ptr, const char *mat)
//{
//        int len_ptr=strlen(ptr);
//        int len_mat=strlen(mat);
//        int next[50];
//        int i,j,pos;
//	      int n ;
//        pos=0;
//        for(i=0;i<50;i++)
//        {
//                next[i]=len_mat+1;
//        }

//        for(i=0;i<len_mat;i++)
//        {
//                        next[mat[i]-'a']=len_mat-i;
//        }
//        while(pos<(len_ptr-len_mat+1))
//        {
//                n=pos ;
//                for(j=0;j<len_mat;j++,n++)
//                {
//                        if(ptr[n]!=mat[j])
//                        {
//                                pos += next[ptr[pos+len_mat]-'a'];
//                                break;
//                        }
//                }
//                if(j==len_mat)
//                        return 1;
//        }
//        return 0;
//}







