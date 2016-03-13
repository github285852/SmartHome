#include "common.h"
#include "stdlib.h"
#include "ds18b20.h"
#include "rc522_function.h"
#include "rc522_config.h"
#include "string.h"
#include "led.h"
#include "beep.h"
/////////////////////////////////////////////////////////////////////////////////////////////////////////// 
//±¾³ÌÐòÖ»¹©Ñ§Ï°Ê¹ÓÃ£¬Î´¾­×÷ÕßÐí¿É£¬²»µÃÓÃÓÚÆäËüÈÎºÎÓÃÍ¾
//ALIENTEK STM32¿ª·¢°å
//ATK-ESP8266 WIFIÄ£¿é WIFI STAÇý¶¯´úÂë	   
//ÕýµãÔ­×Ó@ALIENTEK
//¼¼ÊõÂÛÌ³:www.openedv.com
//ÐÞ¸ÄÈÕÆÚ:2015/4/3
//°æ±¾£ºV1.0
//°æÈ¨ËùÓÐ£¬µÁ°æ±Ø¾¿¡£
//Copyright(C) ¹ãÖÝÊÐÐÇÒíµç×Ó¿Æ¼¼ÓÐÏÞ¹«Ë¾ 2009-2019
//All rights reserved									  
/////////////////////////////////////////////////////////////////////////////////////////////////////////// 

//ATK-ESP8266 WIFI STA²âÊÔ
//ÓÃÓÚ²âÊÔTCP/UDPÁ¬½Ó
//·µ»ØÖµ:0,Õý³£
//    ÆäËû,´íÎó´úÂë
u8 netpro=0;	//ÍøÂçÄ£Ê½
u8 atk_8266_wifista_test(void)
{ u8 cnt=0;
	//u8 netpro=0;	//ÍøÂçÄ£Ê½
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
	u8 ipbuf[16]; 	//IP»º´æ
	u8 *p;
	u16 t=999;		//¼ÓËÙµÚÒ»´Î»ñÈ¡Á´½Ó×´Ì¬
	u8 res=0;
	u16 rlen=0;
	u8 constate=0;	//Á¬½Ó×´Ì¬
	
	p=mymalloc(SRAMIN,32);							//ÉêÇë32×Ö½ÚÄÚ´æ
	atk_8266_send_cmd("AT+CWMODE=1","OK",50);		//ÉèÖÃWIFI STAÄ£Ê½
	atk_8266_send_cmd("AT+RST","OK",20);		//DHCP·þÎñÆ÷¹Ø±Õ(½öAPÄ£Ê½ÓÐÐ§) 
	delay_ms(1000);         //ÑÓÊ±3SµÈ´ýÖØÆô³É¹¦
	delay_ms(1000);
	delay_ms(1000);
	delay_ms(1000);
	//ÉèÖÃÁ¬½Óµ½µÄWIFIÍøÂçÃû³Æ/¼ÓÃÜ·½Ê½/ÃÜÂë,Õâ¼¸¸ö²ÎÊýÐèÒª¸ù¾ÝÄú×Ô¼ºµÄÂ·ÓÉÆ÷ÉèÖÃ½øÐÐÐÞ¸Ä!! 
	sprintf((char*)p,"AT+CWJAP=\"%s\",\"%s\"",wifista_ssid,wifista_password);//ÉèÖÃÎÞÏß²ÎÊý:ssid,ÃÜÂë
	while(atk_8266_send_cmd(p,"WIFI GOT IP",300));					//Á¬½ÓÄ¿±êÂ·ÓÉÆ÷,²¢ÇÒ»ñµÃIP

	netpro|=atk_8266_netpro_sel(50,30,(u8*)ATK_ESP8266_CWMODE_TBL[0]);	//Ñ¡ÔñÍøÂçÄ£Ê½

	     //TCP
				LCD_Clear(WHITE);
				POINT_COLOR=RED;
				Show_Str_Mid(0,30,"ATK-ESP WIFI-STA ²âÊÔ",16,240); 
				Show_Str(30,50,200,16,"ÕýÔÚÅäÖÃATK-ESPÄ£¿é,ÇëÉÔµÈ...",12,0);
				atk_8266_send_cmd("AT+CIPMUX=1","OK",20);   //0£ºµ¥Á¬½Ó£¬1£º¶àÁ¬½Ó
				sprintf((char*)p,"AT+CIPSERVER=1,%s",(u8*)portnum);    //¿ªÆôServerÄ£Ê½(0£¬¹Ø±Õ£»1£¬´ò¿ª)£¬¶Ë¿ÚºÅÎªportnum
				atk_8266_send_cmd(p,"OK",50);   


		  LCD_Clear(WHITE);
			POINT_COLOR=RED;
			Show_Str_Mid(0,30,"ATK-ESP WIFI-STA ²âÊÔ",16,240);
			Show_Str(30,50,200,16,"ÕýÔÚÅäÖÃATK-ESPÄ£¿é,ÇëÉÔµÈ...",12,0);			
			LCD_Fill(30,50,239,50+12,WHITE);			//Çå³ýÖ®Ç°µÄÏÔÊ¾
			Show_Str(30,50,200,16,"WK_UP:ÍË³ö²âÊÔ  KEY0:·¢ËÍÊý¾Ý",12,0);
			LCD_Fill(30,80,239,80+12,WHITE);
			atk_8266_get_wanip(ipbuf);//·þÎñÆ÷Ä£Ê½,»ñÈ¡WAN IP
			sprintf((char*)p,"IPµØÖ·:%s ¶Ë¿Ú:%s",ipbuf,(u8*)portnum);
			Show_Str(30,65,200,12,p,12,0);				//ÏÔÊ¾IPµØÖ·ºÍ¶Ë¿Ú	
			Show_Str(30,80,200,12,"×´Ì¬:",12,0); 		//Á¬½Ó×´Ì¬
			Show_Str(120,80,200,12,"Ä£Ê½:",12,0); 		//Á¬½Ó×´Ì¬
			Show_Str(30,100,200,12,"·¢ËÍÊý¾Ý:",12,0); 	//·¢ËÍÊý¾Ý
			Show_Str(30,115,200,12,"½ÓÊÕÊý¾Ý:",12,0);	//½ÓÊÕÊý¾Ý
			atk_8266_wificonf_show(30,180,"ÇëÉèÖÃÂ·ÓÉÆ÷ÎÞÏß²ÎÊýÎª:",(u8*)wifista_ssid,(u8*)wifista_encryption,(u8*)wifista_password);
			POINT_COLOR=BLUE;
			Show_Str(120+30,80,200,12,(u8*)ATK_ESP8266_WORKMODE_TBL[netpro],12,0); 		//Á¬½Ó×´Ì¬
			USART3_RX_STA=0;
			while(1)
			{
			       cnt ++  ;
			      temp=DS18B20_Get_Temp();
			    	if(SHAKE==0)
				   {
				    atk_8266_send_cmd("AT+CIPSEND=0,5","OK",200);  //·¢ËÍÖ¸¶¨³¤¶ÈµÄÊý¾Ý
					 
						atk_8266_send_data(shake,"OK",100);  //·¢ËÍÖ¸¶¨³¤¶ÈµÄÊý¾Ý
				   }
					  if((temp/10)>=30)
            {
						    BEEP=1 ;
						}
						else
						{
						   BEEP= 0;
						}
				    sprintf((char*)p,"t:%02d.%dC",temp/10,temp%10);//²âÊÔÊý¾Ý
				    // sprintf((char*)p,"1111111111111111111\n");
						Show_Str(30+54,100,200,12,p,12,0);
						atk_8266_send_cmd("AT+CIPSEND=0,7","OK",200);  //·¢ËÍÖ¸¶¨³¤¶ÈµÄÊý¾Ý
					 
						atk_8266_send_data(p,"OK",100);  //·¢ËÍÖ¸¶¨³¤¶ÈµÄÊý¾Ý
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
				atk_8266_send_cmd("AT+CIPSEND=0,20","OK",200);  //·¢ËÍÖ¸¶¨³¤¶ÈµÄÊý¾Ý
			//	delay_ms(200);
				atk_8266_send_data((u8 *)cStr,"OK",100);  //·¢ËÍÖ¸¶¨³¤¶ÈµÄÊý¾Ý
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
			   
				if(USART3_RX_STA&0X8000)		//½ÓÊÕµ½Ò»´ÎÊý¾ÝÁË
				{  
					a=(char *)(USART3_RX_BUF);
//					printf("///////// %s",USART3_RX_BUF);	//·¢ËÍµ½´®¿
//					printf("*********    %s\n\n",a);
   			
			 	if(match_string(a,"open")== 0)
					{
					   LED0=!LED0 ;
						 
					}
					else{
						LED1=!LED1 ;
					   flag = 1;
					}
					

			    rlen=USART3_RX_STA&0X7FFF;	//µÃµ½±¾´Î½ÓÊÕµ½µÄÊý¾Ý³¤¶È
					USART3_RX_BUF[rlen]=0;		//Ìí¼Ó½áÊø·û 
			//		printf("%s",USART3_RX_BUF);	//·¢ËÍµ½´®¿Ú   
					sprintf((char*)p,"ÊÕµ½%d×Ö½Ú,ÄÚÈÝÈçÏÂ",rlen);//½ÓÊÕµ½µÄ×Ö½ÚÊý 
					LCD_Fill(30+54,115,239,130,WHITE);
					POINT_COLOR=BRED;
					Show_Str(30+54,115,156,12,p,12,0); 			//ÏÔÊ¾½ÓÊÕµ½µÄÊý¾Ý³¤¶È
					POINT_COLOR=BLUE;
					LCD_Fill(30,130,239,319,WHITE);
        
					Show_Str(30,130,180,190,USART3_RX_BUF,12,0);//ÏÔÊ¾½ÓÊÕµ½µÄÊý¾Ý  
					
					USART3_RX_STA=0;
					if(constate!='+')t=1000;		//×´Ì¬Îª»¹Î´Á¬½Ó,Á¢¼´¸üÐÂÁ¬½Ó×´Ì¬
					else t=0;                   //×´Ì¬ÎªÒÑ¾­Á¬½ÓÁË,10ÃëºóÔÙ¼ì²é
				}  
				if(t==1000)//Á¬Ðø10ÃëÖÓÃ»ÓÐÊÕµ½ÈÎºÎÊý¾Ý,¼ì²éÁ¬½ÓÊÇ²»ÊÇ»¹´æÔÚ.
				{
//			//		LCD_Fill(30+54,125,239,130,WHITE);
//					LCD_Fill(60,80,120,92,WHITE);
					constate=atk_8266_consta_check();//µÃµ½Á¬½Ó×´Ì¬
					if(constate=='+')Show_Str(30+30,80,200,12,"Á¬½Ó³É¹¦",12,0);  //Á¬½Ó×´Ì¬
					else Show_Str(30+30,80,200,12,"Á¬½ÓÊ§°Ü",12,0); 	 
					t=0;
				}
				
				if((t%20)==0);
				atk_8266_at_response(1);

			}
	myfree(SRAMIN,p);		//ÊÍ·ÅÄÚ´æ 
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







