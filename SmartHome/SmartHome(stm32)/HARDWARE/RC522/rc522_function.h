#ifndef __RC522_FUNCTION_H
#define	__RC522_FUNCTION_H


#include "sys.h"


#define          macDummy_Data              0x00

void IC_test ( void )   ;
void             PcdReset                   ( void );                       //��λ
void             M500PcdConfigISOType       ( u8 type );                    //������ʽ
char             PcdRequest                 ( u8 req_code, u8 * pTagType ); //Ѱ��
char             PcdAnticoll                ( u8 * pSnr);                   //������


#endif /* __RC522_FUNCTION_H */

