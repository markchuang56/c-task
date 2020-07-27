#include "stdio.h"
#include "stdlib.h"
#include "math.h"

void errorParameter(void);

int main(int argc, const char *argv[]) {
	printf("==== Command Paramter test ====\n");
	if (argc <= 4) {
		errorParameter();
		exit(1);
	}

	for(int k=0; k<argc; k++) {
		
		printf("==== The Command is %d and %s ====\n", k, argv[k]);
	}
	exit(0);
}

void errorParameter(void) {
	printf("==== MAX Command Format ====\n");
	
	printf("==== MAX Command Format ====\n");
	printf("==== MAX Command Format ====\n");
	printf("==== MAX Command Format ====\n");
	printf("==== MAX Command Format ====\n");
	printf("==== MAX Command Format ====\n");

}
