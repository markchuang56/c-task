#include <stdio.h>
#include <stdlib.h>

#include <math.h>

void func(void);

int main(int argc, const char *argv[]) {
	printf("This is a GIT Meger Example!!\n");
	
	func();
	exit(0);
}

void func(void) {
	for(int i=0; i<10; i++) {
		printf("THE VALUE i = %d\n", i);
		// WHAT HAPPEN ???
	}
}
