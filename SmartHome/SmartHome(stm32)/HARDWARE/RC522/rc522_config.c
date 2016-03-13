#include "rc522_config.h"
#include "rc522_function.h"
#include "delay.h"
static void             RC522_SPI_Config             ( void );



void RC522_Init ( void )
{
	
  RC522_SPI_Config ();
	
	macRC522_Reset_Disable();
	
	macRC522_CS_Disable();

}


static void RC522_SPI_Config ( void )
{
	GPIO_InitTypeDef  GPIO_InitStructure;
  SPI_InitTypeDef  SPI_InitStructure;
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOC, ENABLE);//使能GPIOB时钟
  RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOA, ENABLE);//使能GPIOA时钟
  RCC_APB1PeriphClockCmd(RCC_APB1Periph_SPI3, ENABLE);//使能SPI2时钟
 
  //GPIOFB3,4,5初始化设置
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_10|GPIO_Pin_11|GPIO_Pin_12;//PB3~5复用功能输出	
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF;//复用功能
  GPIO_InitStructure.GPIO_OType = GPIO_OType_PP;//推挽输出
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_100MHz;//100MHz
  GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_UP;//上拉
  GPIO_Init(GPIOC, &GPIO_InitStructure);//初始化
	
	GPIO_PinAFConfig(GPIOC,GPIO_PinSource10,GPIO_AF_SPI3); //PB13复用为 SPI1
	GPIO_PinAFConfig(GPIOC,GPIO_PinSource11,GPIO_AF_SPI3); //PB14复用为 SPI1
	GPIO_PinAFConfig(GPIOC,GPIO_PinSource12,GPIO_AF_SPI3); //PB15复用为 SPI1
 
	//这里只针对SPI口初始化
//	RCC_APB2PeriphResetCmd(RCC_APB2Periph_SPI1,ENABLE);//复位SPI1
//	RCC_APB2PeriphResetCmd(RCC_APB2Periph_SPI1,DISABLE);//停止复位SPI1

	SPI_InitStructure.SPI_Direction = SPI_Direction_2Lines_FullDuplex;  //设置SPI单向或者双向的数据模式:SPI设置为双线双向全双工
	SPI_InitStructure.SPI_Mode = SPI_Mode_Master;		//设置SPI工作模式:设置为主SPI
	SPI_InitStructure.SPI_DataSize = SPI_DataSize_8b;		//设置SPI的数据大小:SPI发送接收8位帧结构
	SPI_InitStructure.SPI_CPOL = SPI_CPOL_High;		//串行同步时钟的空闲状态为高电平
	SPI_InitStructure.SPI_CPHA = SPI_CPHA_2Edge;	//串行同步时钟的第二个跳变沿（上升或下降）数据被采样
	SPI_InitStructure.SPI_NSS = SPI_NSS_Soft;		//NSS信号由硬件（NSS管脚）还是软件（使用SSI位）管理:内部NSS信号有SSI位控制
	SPI_InitStructure.SPI_BaudRatePrescaler = SPI_BaudRatePrescaler_256;		//定义波特率预分频的值:波特率预分频值为256
	SPI_InitStructure.SPI_FirstBit = SPI_FirstBit_MSB;	//指定数据传输从MSB位还是LSB位开始:数据传输从MSB位开始
	SPI_InitStructure.SPI_CRCPolynomial = 7;	//CRC值计算的多项式
	SPI_Init(SPI3, &SPI_InitStructure);  //根据SPI_InitStruct中指定的参数初始化外设SPIx寄存器
 
	SPI_Cmd(SPI3, ENABLE); //使能SPI外设

	SPI3_ReadWriteByte(0xff);//启动传输		 
	
	
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOB, ENABLE);//使能GPIOB时钟
  RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOG, ENABLE);//使能GPIOG时钟

	  //GPIOB14
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_14;//PB14
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_OUT;//输出
  GPIO_InitStructure.GPIO_OType = GPIO_OType_PP;//推挽输出
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_100MHz;//100MHz
  GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_DOWN;//上拉
  GPIO_Init(GPIOB, &GPIO_InitStructure);//初始化

	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_6;//PG7
  GPIO_Init(GPIOG, &GPIO_InitStructure);//初始化
 
	GPIO_SetBits(GPIOG,GPIO_Pin_6);//PG7输出1,防止NRF干扰SPI FLASH的通信 

	SPI3_SetSpeed(SPI_BaudRatePrescaler_4);		//设置为42M时钟,高速模式 

	
  /* SPI_InitTypeDef  SPI_InitStructure */
//  GPIO_InitTypeDef GPIO_InitStructure;
//  

//	/*!< Configure SPI_RC522_SPI pins: CS */
//	macRC522_GPIO_CS_CLK_FUN ( macRC522_GPIO_CS_CLK, ENABLE );
//  GPIO_InitStructure.GPIO_Pin = macRC522_GPIO_CS_PIN;
//  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
//  GPIO_InitStructure.GPIO_Mode = macRC522_GPIO_CS_Mode;
//  GPIO_Init(macRC522_GPIO_CS_PORT, &GPIO_InitStructure);
//	
//  /*!< Configure SPI_RC522_SPI pins: SCK */
//	macRC522_GPIO_SCK_CLK_FUN ( macRC522_GPIO_SCK_CLK, ENABLE );
//  GPIO_InitStructure.GPIO_Pin = macRC522_GPIO_SCK_PIN;
//  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
//  GPIO_InitStructure.GPIO_Mode = macRC522_GPIO_SCK_Mode;
//  GPIO_Init(macRC522_GPIO_SCK_PORT, &GPIO_InitStructure);
//	
//  /*!< Configure SPI_RC522_SPI pins: MOSI */
//	macRC522_GPIO_MOSI_CLK_FUN ( macRC522_GPIO_MOSI_CLK, ENABLE );
//  GPIO_InitStructure.GPIO_Pin = macRC522_GPIO_MOSI_PIN;
//  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
//  GPIO_InitStructure.GPIO_Mode = macRC522_GPIO_MOSI_Mode;
//  GPIO_Init(macRC522_GPIO_MOSI_PORT, &GPIO_InitStructure);

//  /*!< Configure SPI_RC522_SPI pins: MISO */
//	macRC522_GPIO_MISO_CLK_FUN ( macRC522_GPIO_MISO_CLK, ENABLE );
//  GPIO_InitStructure.GPIO_Pin = macRC522_GPIO_MISO_PIN;
//  GPIO_InitStructure.GPIO_Mode = macRC522_GPIO_MISO_Mode;
//  GPIO_Init(macRC522_GPIO_MISO_PORT, &GPIO_InitStructure);	
//	
//	
//  /*!< Configure SPI_RC522_SPI pins: RST */
//	macRC522_GPIO_RST_CLK_FUN ( macRC522_GPIO_RST_CLK, ENABLE );
//  GPIO_InitStructure.GPIO_Pin = macRC522_GPIO_RST_PIN;
//  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
//  GPIO_InitStructure.GPIO_Mode = macRC522_GPIO_RST_Mode;
//  GPIO_Init(macRC522_GPIO_RST_PORT, &GPIO_InitStructure);
	
}

//SPI1速度设置函数
//SPI速度=fAPB2/分频系数
//@ref SPI_BaudRate_Prescaler:SPI_BaudRatePrescaler_2~SPI_BaudRatePrescaler_256  
//fAPB2时钟一般为84Mhz：
void SPI3_SetSpeed(u8 SPI_BaudRatePrescaler)
{
  assert_param(IS_SPI_BAUDRATE_PRESCALER(SPI_BaudRatePrescaler));//判断有效性
	SPI3->CR1&=0XFFC7;//位3-5清零，用来设置波特率
	SPI3->CR1|=SPI_BaudRatePrescaler;	//设置SPI1速度 
	SPI_Cmd(SPI3,ENABLE); //使能SPI1
} 
//SPI1 读写一个字节
//TxData:要写入的字节
//返回值:读取到的字节
u8 SPI3_ReadWriteByte(u8 TxData)
{		 			 
 
   while (SPI_I2S_GetFlagStatus(SPI3, SPI_I2S_FLAG_TXE) == RESET){}//等待发送区空  
	
	SPI_I2S_SendData(SPI3, TxData); //通过外设SPIx发送一个byte  数据
		
  while (SPI_I2S_GetFlagStatus(SPI3, SPI_I2S_FLAG_RXNE) == RESET){} //等待接收完一个byte  
 
	return SPI_I2S_ReceiveData(SPI3); //返回通过SPIx最近接收的数据	
}









