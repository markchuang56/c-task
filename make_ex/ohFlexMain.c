#include "stdio.h"
#include "string.h"
#include "ohFlex.h"

// ex, Data
unsigned char flexSrc[64] = {0};
int main(void)
{
	printf("How Do You Turn This On!!\n");
	
	unsigned char hi, lo;
	
	int date = 0x7FC49978;
	int time = 0xF70790C5;
	
	unsigned char tmpDate[16] = {0};
	unsigned char tmpTime[16] = {0};
	
	unsigned char tmpX[4] = {0};
	unsigned char tmpY[4] = {0};
	
	unsigned short flexDataLen = ((4 * 4) << 1) + 2;
	unsigned char flexDataSrc[64] = {0};
	
	memcpy(tmpX, &date, 4);	
	memcpy(tmpY, &time, 4);		
	printf("======== SRC OH ==========\n");
	for(int i=0; i<4; i++){
		
		hi = ohToAscii((tmpX[i] & 0xF0) >> 4);
		lo = ohToAscii(tmpX[i] & 0x0F);
		tmpDate[4*i] = hi;
		tmpDate[4*i + 2] = lo;
		
		hi = ohToAscii((tmpY[i] & 0xF0) >> 4);
		lo = ohToAscii(tmpY[i] & 0x0F);
		tmpTime[4*i] = hi;
		tmpTime[4*i + 2] = lo;
		printf("FLEX %02d, %02X %02X \n", i, tmpX[i], tmpY[i]);
	}	
	memcpy(&flexDataSrc[DATA_OFFSET], tmpDate, 16);
	memcpy(&flexDataSrc[DATA_OFFSET+16], tmpTime, 16);
	flexDataSrc[DATA_OFFSET+16] = 0;
	flexDataSrc[DATA_OFFSET+16+2] = 0;
	
	flexDataProcess(flexDataSrc, flexDataLen, MEM_ADDR);
	return 0;
}