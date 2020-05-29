#include <stdio.h>
#include <stdlib.h>

#include <math.h>

void func(void);
#define N		8
struct Complex {
	double real;
	double imag;
};


int main(int argc, const char *argv[]) {
	printf("This is a GIT Meger Example!!\n");
	
	struct Complex complex;
	for(int i=0; i<N; i++) {
		complex[i].real = cos(i/N);
		complex[i].imag = sin(i/N);

		printf("=========== Complex Test ===========\n");
		pritnf("The Val %d\t%8.5f\t%8.5f\n", i, complex[i].real, complex[i].imag);
	}

	func();
	exit(0);
}

void func(void) {
	for(int i=0; i<10; i++) {
		printf("THE VALUE i = %d\n", i);
		// WHAT HAPPEN ???
	}
}
