#include <stdio.h>

int main(int argc, char *argv[])
{
	printf("Read/Write file ...\n");
	if(argc != 2) {
		printf("Please give a file name!!\n");
		//return 1;
	}
	FILE *fp;
	char ch;
	//fopen() function is used to open a file name xf.txt in read mode
	fp = fopen("xf.txt", "r");
	fseek(fp, 0L, SEEK_END);
	int fLen = ftell(fp);
	printf("FILE LEN = %d\n", fLen);
	rewind(fp);
	//ch=getc(fp);
	// fread(pbufB, 1, szb, fpB);
	while ((ch=getc(fp)) != EOF) {
		printf("== %02X, %c ==\n", ch, ch);
	}
	printf("&&&&&&&&&&&&&\n");
	printf("== %02X, %c ==\n", ch, ch);
	//if (fp != null) {
	//	printf("OPEN file succed!!\n");
	//}
	// fclose() is used to close the file
	fclose(fp);
	return 0;
}