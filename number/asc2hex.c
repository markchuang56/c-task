#include "stdio.h"

int test (int i);
int fn (int n);

int main(void)
{
	unsigned char nr = 0;
	for(int i=0x80; i<256; i++)
	{
		nr = i;
		nr ^= 0xFF;
		nr += 1;
		printf("IDX = - %02d, VAL = %02X\n", nr, i);
	}
	printf("what ?? \n");
	

	int n = 8;
	
	int i, j, k, x=0;
	for (i = 1; i <= n; i++){
		for (j = 1; j <= i; j++) {
			for (k = 1; k <= j; k++){
				x++;
			}
		}
	}
		
	printf("THE x = %d\n", x);
	int z;
	int s = 0;
	for (k=1; k<=3; k++) {
		//printf("THE s = %d\n", z);
		for (z=1; z<= 4-k; z++) { 
			s += (k * z);
			printf("THE s = %d\n", s);
		} 
	}
	printf("THE s = %d\n", s);
	
	//int sumx = test(5);
	int sumx = fn (12345);
	printf("THE SUM = %d\n", sumx);
	return 0;
}

int test (int i)
{
	int sum;
	printf(" id  = %d\n", i);
	if(i <=1){
		printf(" === END ===\n\n");
		return i;
	}
	
		
	sum = test(i-1)+test(i-2);
	printf(" id  = %d, AND SUM = %d\n", i, sum);
	return sum;
		
}

int fn (int n) { 
	printf(" id  = %d\n", n);
	 if (n<3)
		 return 2; 
	 else if (n<5)
		 return fn(n-1)+2; 
	 else
		 return fn(n/3)+4; 
}