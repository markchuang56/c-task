#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>

int main(int argc, char *argv[])
{
	if (argc != 2) {
		printf("error happen!!\n");
		printf("One file name is Need!!\n");
		return 1;
	}
   int ch;
   int flags = fcntl(STDIN_FILENO, F_GETFL);
   printf("STEP == 0\n");
   if(flags == -1) return -1; // error

   fcntl(STDIN_FILENO, F_SETFL, flags | O_NONBLOCK);
   printf("STEP == 1\n");
   ch = fgetc(stdin);
   if(ch == EOF) return -1;
   printf("STEP == 2\n");
   if(ch == -1) return -1;
   printf("STEP == 3\n");
   printf("%c", ch);
   return 0;
}
