#include "stdio.h"
#include "stdlib.h"
#include "math.h"

int main(int argc, const char *argv[]) {
	printf("0123456789ABCDEF\n\n\n");
	for(int i=1; i<argc; i++) {
		printf("command %02d, %s\n", i, argv[i]);
	}
	exit(0);
}
