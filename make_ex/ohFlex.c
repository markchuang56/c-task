#include "stdio.h"
#include "string.h"
#include "ohFlex.h"

unsigned char flexDate[] =
{
	0x03, 
	0x02, 0x2a, 0x00 , 0x04, 0x06, 0x36, 0x00 , 
	0x38, 0x00, 0x41, 0x00 , 0x46, 0x00, 0x31, 0x00 , 
	0x36, 0x00, 0x45, 0x00 ,
	0x41, 0x30, 0x00, 0x44 , 0x00, 0x32, 0x00, 0x32 , 
	0x00, 0x43, 0x00, 0x32 , 0x00, 0x46, 0x00, 0x34 ,
	0x00, 0x42, 0x00, 0x00 ,
	0x42, 0x00, 0x03, 0x80 , 0x39
};

unsigned char flexDate1[] =
{
	0x03, 0x02, 0x2a, 0x00 , 0x04, 0x06, 0x39, 0x00 ,
	0x41, 0x00, 0x38, 0x00 , 0x36, 0x00, 0x31, 0x00 ,
	0x43, 0x00, 0x31, 0x00 ,
	
	0x41, 0x41, 0x00, 0x32 , 0x00, 0x31, 0x00, 0x42 , 
	0x00, 0x46, 0x00, 0x30 , 0x00, 0x31, 0x00, 0x45 ,
	0x00, 0x34, 0x00, 0x00 ,
		
	0x42, 0x00, 0x03, 0x6f , 0x98
};

unsigned char flexDate2[] =
{
	0x03, 0x02, 0x2a, 0x00 , 0x04, 0x06, 0x37, 0x00 ,
	0x38, 0x00, 0x39, 0x00 , 0x39, 0x00, 0x43, 0x00 ,
	0x34, 0x00, 0x37, 0x00 ,
	0x41, 0x46, 0x00, 0x43 , 0x00, 0x35, 0x00, 0x39 ,
	0x00, 0x30, 0x00, 0x30 , 0x00, 0x37, 0x00, 0x46 ,
	0x00, 0x37, 0x00, 0x00 ,
	0x42, 0x00, 0x03, 0x58 , 0xeb
};

// 68 24 53
//0x6d, 0x01, 0x02, 0x00 , 0x8e, 0x2c, 0x82, 0xd8 , 
//0x5a, 0xce, 0xe4, 0xc4 , 0xbd, 0xe9, 0xaf, 0x13 , 
//0x2a, 0x45, 0x33, 0x99
unsigned char flexDate3[] =
{
0x03, 0x02, 0x2a, 0x00 , 0x04, 0x06, 0x43, 0x00 , 
0x45, 0x00, 0x41, 0x00 , 0x43, 0x00, 0x45, 0x00 ,
0x34, 0x00, 0x37, 0x00 ,
0x41, 0x36, 0x00, 0x44 , 0x00, 0x39, 0x00, 0x34 , 
0x00, 0x37, 0x00, 0x39 , 0x00, 0x44, 0x00, 0x37 , 
0x00, 0x43, 0x00, 0x00 ,
0x42, 0x00, 0x03, 0x64 , 0x69
};

//unsigned char flexResult[128] = {0xFF};

//unsigned char flexSrc[64] = {0};



unsigned short good_crc; 
unsigned short bad_crc; 

unsigned char result_crc[4] = {0};

/*
{
0x03, 0x02, 0x2a, 0x00 , 0x04, 0x06, 0x43, 0x00 , 
0x45, 0x00, 0x41, 0x00 , 0x43, 0x00, 0x45, 0x00 ,
0x34, 0x00, 0x37, 0x00 ,
0x41, 0x36, 0x00, 0x44 , 0x00, 0x39, 0x00, 0x34 , 
0x00, 0x37, 0x00, 0x39 , 0x00, 0x44, 0x00, 0x37 , 
0x00, 0x43, 0x00, 0x00 ,
0x42, 0x00, 0x03, 0x64 , 0x69
};
*/

#if 0

int main(void)
{
	int len = 0;
	unsigned char hi, lo;
	len = flexDate2[2];
	//int date = 0x7899C47F;
	//int time = 0xC59007F7;
	int date = 0x7FC49978;
	int time = 0xF70790C5;
	
	unsigned char tmpDate[16] = {0};
	unsigned char tmpTime[16] = {0};
	
	unsigned char tmpX[4] = {0};
	unsigned char tmpY[4] = {0};
	
	unsigned char buffer[LEN_TOTAL] = {0};
	unsigned char bufferA[LEN_TOTAL>>1] = {0};
	
	memcpy(buffer, &flexDate2[6], LEN_TX);
	memcpy(&buffer[LEN_TX], &flexDate2[21], LEN_TOTAL-LEN_TX);
	
	//memcpy(buffer[], &flexDate2[]);
	printf("LEN is %d, %04X\n", len, len);
	for(int i=0; i<LEN_TOTAL; i++)
	{
		printf("FLEX %02d, %02X \n", i, buffer[i]);
	}
	
	printf("==================\n");
	for(int i=0; i<(LEN_TOTAL>>1); i++)
	{
		bufferA[i] = buffer[2*i];
		printf("FLEX %02d, %02X \n", i, bufferA[i]);
	}
	
	printf("======== 222 ==========\n");
	for(int i=0; i<(LEN_TOTAL>>2); i++){
		hi = transfer(bufferA[2*i]);
		lo = transfer(bufferA[2*i+1]);
		printf("FLEX %02d, %02X \n", i, (hi<<4) + lo);
	}
	
	//date = 0x7899C47F;
	//	int time
	printf("LEN = %lu\n", sizeof(int));
	memcpy(tmpX, &date, 4);	
	memcpy(tmpY, &time, 4);		
	printf("======== SRC ==========\n");
	for(int i=0; i<4; i++){
		
		hi = ohToAscii((tmpX[i] & 0xF0) >> 4);
		lo = ohToAscii(tmpX[i] & 0x0F);
		
		tmpDate[4*i] = hi;
		//tmpDate[4*i+1] = '0';
		tmpDate[4*i +2] = lo;
		//tmpDate[4*i+3] = '0';
		
		hi = ohToAscii((tmpY[i] & 0xF0) >> 4);
		lo = ohToAscii(tmpY[i] & 0x0F);
		
		tmpTime[4*i] = hi;
		//tmpTime[4*i+1] = '0';
		tmpTime[4*i +2] = lo;
		//tmpTime[4*i+3] = '0';
		
		//ohToAscii
		//hi = transfer(bufferA[2*i]);
		//lo = transfer(bufferA[2*i+1]);
		printf("FLEX %02d, %02X %02X \n", i, tmpX[i], tmpY[i]);
	}
	
	printf("\n");
	printf("======== RESULT ==========\n");
	for(int i=0; i<16; i++){
		printf("FLEX %02d, D = %02X, T = %02X \n", i, tmpDate[i], tmpTime[i]);
	}
	printf("\n");
	
	printf("======== RAW ==========\n");
	unsigned short dataLen = 0;
	dataLen = 1 + 2 + 2 + 4*4 + 4*4 + 2 + 1 + 2;
	flexSrc[ST_OFFSET] = ST;
	memcpy(&flexSrc[LEN_OFFSET], &dataLen, 2);
	
	flexSrc[MEM_OFFSET] = (unsigned char)(MEM_ADDR >> 8);
	flexSrc[MEM_OFFSET+1] = (unsigned char)(MEM_ADDR % 256);
	memcpy(&flexSrc[DATA_OFFSET], tmpDate, 16);
	memcpy(&flexSrc[DATA_OFFSET+16], tmpTime, 16);
	
	flexSrc[DATA_OFFSET+16+16] = 0;
	flexSrc[DATA_OFFSET+16+16+1] = 0;
	
	flexSrc[DATA_OFFSET+16+16+2] = EOT;
	
	for(int i=0; i<dataLen; i++){
		printf("RD  %d, = %02X \n", i, flexSrc[i]);
	}

	goFlex(flexSrc, dataLen-2);
	
	memcpy(&flexSrc[dataLen-2], &bad_crc, 2);
	// flexResult
	// FLEX_DIV
	for(int i=0; i<128; i++){
		flexResult[i] = 0xFF;
	}
	flexResult[0] = dataLen/FLEX_DIV + 1;
	
	for(int i=0; i<(dataLen/FLEX_DIV) + 1; i++){
		if(dataLen>(i+1)*FLEX_DIV){
			memcpy(&flexResult[1+i*(FLEX_DIV+1)], &flexSrc[i*FLEX_DIV], FLEX_DIV);
		}else{
			memcpy(&flexResult[1+i*(FLEX_DIV+1)], &flexSrc[i*FLEX_DIV], dataLen-FLEX_DIV*i);
		}
		if(i>0){
			flexResult[i*(FLEX_DIV+1)] = 'A'+i-1;
		}
		
	}	
	
	printf("======== THE END ==========\n");
	for(int i=0; i<=dataLen+20; i++){
		printf(" VAL %d, %02X \n", i, flexResult[i]);
	}
	printf("GOOD OH!!");
	return 0;
}
#endif

//unsigned char flexSrc[64] = {0};
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
	memcpy(&flexDataSrc[0], tmpDate, 16);
	memcpy(&flexDataSrc[16], tmpTime, 16);
	flexDataSrc[32] = 0;
	flexDataSrc[32+1] = 0;
	// Debug
	printf("=========== SRC DATA ===========\n");
	for(int i=0; i<flexDataLen ; i++){
		printf("SRC = %d, %02X\n",i ,flexDataSrc[i]);
	}
	//flexDataProcess(flexDataSrc, flexDataLen, MEM_ADDR);
	flexDataProcess(flexDataSrc, 0, MEM_ADDR);
	return 0;
}

#define OH_ST_LEN		1
#define OH_LEN_LEN		2
#define OH_MEM_LEN		2
#define OH_EOT_LEN		1
#define OH_CRC_LEN		2
unsigned char gFlexBuffer[128] = {0xFF};
void flexDataProcess(unsigned char* srcData, unsigned short dataLen, unsigned short memAddr)
{
	printf("======== RAW ==========\n");
	for(int i=0; i<sizeof(gFlexBuffer); i++){
		gFlexBuffer[i] = 0xFF;
	}
	unsigned short totalLen = 0;
	unsigned char localBuffer[64] = {0};
	//dataLength = 1 + 2 + 2 + 4*4 + 4*4 + 2 + 1 + 2;
	totalLen = 1 + 2 + 2 + dataLen + 1 + 2;
	
	localBuffer[ST_OFFSET] = ST;
	memcpy(&localBuffer[LEN_OFFSET], &totalLen, 2);
	
	localBuffer[MEM_OFFSET] = (unsigned char)(memAddr >> 8);
	localBuffer[MEM_OFFSET+1] = (unsigned char)(memAddr % 256);
	if(dataLen>0){
		memcpy(&localBuffer[DATA_OFFSET], srcData, dataLen);
	}
	
	localBuffer[DATA_OFFSET+dataLen] = EOT;
	
	for(int i=0; i<totalLen; i++){
		printf("RD  %d, = %02X \n", i, localBuffer[i]);
	}

	goFlex(localBuffer, totalLen-2);
	
	memcpy(&localBuffer[totalLen-2], &bad_crc, 2);
	
	
	gFlexBuffer[0] = totalLen/FLEX_DIV + 1;
	
	for(int i=0; i<(totalLen/FLEX_DIV) + 1; i++){
		if(totalLen>(i+1)*FLEX_DIV){
			memcpy(&gFlexBuffer[1+i*(FLEX_DIV+1)], &localBuffer[i*FLEX_DIV], FLEX_DIV);
		}else{
			memcpy(&gFlexBuffer[1+i*(FLEX_DIV+1)], &localBuffer[i*FLEX_DIV], totalLen-FLEX_DIV*i);
		}
		if(i>0){
			gFlexBuffer[i*(FLEX_DIV+1)] = 'A'+i-1;
		}
		
	}	
	
	printf("======== THE END ==========\n");
	for(int i=0; i<=totalLen+20; i++){
		printf(" VAL %d, %02X \n", i, gFlexBuffer[i]);
	}
	printf("GOOD OH!!");
}

unsigned char transfer(char ch)
{
	unsigned char result = '0';
	if(ch >= 'A'){
		result = ch - 'A' + 0xA;
	}else{
		result = ch - '0';
	}
	
	return result;
}

unsigned char ohToAscii(char ch)
{
	unsigned char result = '0';
	if(ch >= 0xA){
		result = ch + 'A' - 0xA;
	}else{
		result = ch + '0';
	}
	
	return result;
}



void goFlex(unsigned char *dataSrc, int srcLen) 
{ 
	//int srcLen = sizeof(dataSrc);

    good_crc = 0xffff; 
    bad_crc = 0xffff;

	for(int i=0; i<srcLen; i++)
    { 
     	update_good_crc(dataSrc[i]); 
     	update_bad_crc(dataSrc[i]); 
  	}

    augment_message_for_good_crc(); 
	
    printf("GET GET - BINGO #### GOOD GOOD, %04X\n", good_crc);
    printf("GET GET - BINGO ####, %04X BAD BAD\n", bad_crc);
	
	memcpy(result_crc, &bad_crc, 2);
	memcpy(&result_crc[2], &good_crc, 2);
	for(int i=0; i<sizeof(result_crc); i++)
	{
		printf("RESULT : %d = %02X \n", i, result_crc[i]);
	}
    printf("\n");
}
/*
void repeat_character(unsigned char ch, unsigned short n) 
{ 
    unsigned short i; 
    for (i=0; i<n; i++) 
    { 
        text[i] = ch; 
    } 
    text[n] = 0; 
}
*/
void update_good_crc(unsigned short ch) 
{ 
    unsigned short i, v, xor_flag;

    /* 
    Align test bit with leftmost bit of the message byte. 
    */ 
    v = 0x80;

    for (i=0; i<8; i++) 
    { 
        if (good_crc & 0x8000) 
        { 
            xor_flag= 1; 
        } 
        else 
        { 
            xor_flag= 0; 
        } 
        good_crc = good_crc << 1;

        if (ch & v) 
        { 
            /* 
            Append next bit of message to end of CRC if it is not zero. 
            The zero bit placed there by the shift above need not be 
            changed if the next bit of the message is zero. 
            */ 
            good_crc= good_crc + 1; 
        }

        if (xor_flag) 
        { 
            good_crc = good_crc ^ poly; 
        }

        /* 
        Align test bit with next bit of the message byte. 
        */ 
        v = v >> 1; 
    }
    //if(good_crc == GOAL){
//      printf("J_GOOD %04X , POLY = %04X \n", good_crc, poly);
    //}
}

void augment_message_for_good_crc() 
{ 
    unsigned short i, xor_flag;

    for (i=0; i<16; i++) 
    { 
        if (good_crc & 0x8000) 
        { 
            xor_flag= 1; 
        } 
        else 
        { 
            xor_flag= 0; 
        } 
        good_crc = good_crc << 1;

        if (xor_flag) 
        { 
            good_crc = good_crc ^ poly; 
        } 
    } 
}

void update_bad_crc(unsigned short ch) 
{ 
    /* based on code found at 
    http://www.programmingparadise.com/utility/crc.html 
    */

    unsigned short i, xor_flag;

    /* 
    Why are they shifting this byte left by 8 bits?? 
    How do the low bits of the poly ever see it? 
    */ 
    ch<<=8;

    for(i=0; i<8; i++) 
    { 
        if ((bad_crc ^ ch) & 0x8000) 
        { 
            xor_flag = 1; 
        } 
        else 
        { 
            xor_flag = 0; 
        } 
        bad_crc = bad_crc << 1; 
        if (xor_flag) 
        { 
            bad_crc = bad_crc ^ poly; 
        } 
        ch = ch << 1; 
    }
}