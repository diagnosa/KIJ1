// how to compile
// gcc -o <nama_hasil_ekseskusi> <file_name>.c
// contoh: gcc -o server_result server.c
// ./<nama hasil eksekusi> (untuk run)
// contoh: ./server_result

#include<stdio.h>
#include<arpa/inet.h>
#include<sys/socket.h>
#include<errno.h>
#include<string.h>
#include<stdlib.h>

void main(int argc, char** argv){
	int sockfd, sockcli;
	int clisize;
	struct sockaddr_in servaddr, cliaddr;

	if((sockfd = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
	{
		//printf("Socket failed\n");
		perror("socket");
		return;
	}
	else
	{
		printf("Socket created\n");
	}
	
	memset(&servaddr, 0, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(6064);
	
	printf("COBA\n");
	
	if(bind(sockfd, (struct sockaddr *)&servaddr, sizeof(struct sockaddr)) < 0)
	{
		// printf("Bind failed\n");
		perror("bind");
		return;
	}
	else
	{
		printf("Bind to port %d\n", servaddr.sin_port);
	}
	
	printf("COBA2\n");
	
	if(listen(sockfd, 5) < 0)
	{
		printf("Listen failed\n");
	}
	else
	{
		printf("Listening to client\n");
	}
	
	printf("COBA3\n");
	
	for(;;)
	{
		memset(&cliaddr, 0, sizeof(cliaddr));
	
		clisize = sizeof(cliaddr);
		
		if((sockcli = accept(sockfd, (struct sockaddr *)&cliaddr, &clisize)) < 0)
		{
			printf("Accept failed\n");
		}
		else
		{
			printf("Ada panggilan dari %s\n", inet_ntoa(cliaddr.sin_addr));
			
		}
	}
	close(sockfd);
}

