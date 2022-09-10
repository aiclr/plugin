#include <stdio.h>
#include <stdlib.h>

int main(int argc,char *argv[])
{
	FILE *IFP=NULL;
	FILE *OFP=NULL;
	IFP=fopen(argv[1],"rb");
	OFP=fopen(argv[2],"wb");
	short element;
	short encode;
	while((element=getc(IFP))!=EOF)
	{
		printf("%2x",element);
		encode=element^1;
		putc(encode,OFP);
	}
	fclose(OFP);
	fclose(IFP);
	return 0;
}

