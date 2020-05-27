#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define N	16
#define PI	3.14159

struct Complex {
	double real;
	double imag;
};

struct Complex data[N<<4];

int main(int argc, const char *argv[]) {
	printf("========== FFT, DFT ===========\n\n");
	for(int m=0; m<(N<<4); m++) {
		data[m].real = cos((float)m/N*PI);
		data[m].imag = sin((float)m/N*PI);

		printf("Complex Value %8.5f\t%8.5f\n", data[m].real, data[m].imag);
	}
	printf("\n");
	exit(0);
}
