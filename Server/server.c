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
	int retval, clisize=0;
	struct sockaddr_in servaddr, cliaddr;

	sockfd = socket(AF_INET, SOCK_STREAM, 0); //hanya untk proses listen
	servaddr.sin_family = AF_INET;
	servaddr.sin_port = htons(6064);
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);

	retval= bind(sockfd, (struct sockaddr *)&servaddr, sizeof(servaddr));
	if(retval < 0){
		perror(strerror(errno));
		exit(1);
	}
	printf("Server mengikat port 6064\n");
	retval = listen(sockfd, 5);
	printf("Server menunggu panggilan....\n");

	bzero(&cliaddr, sizeof(cliaddr));
	clisize= 0;
	sockcli = accept(sockfd, (struct sockaddr *)&cliaddr, &clisize);
	printf("ada panggilan");
	if(sockcli<0){
		perror(strerror(errno));
		exit(-1);
	}
	//BACA PESAN
	  char str[] ="icha|sasa|lalalalala";
	  bzero(str, 100);
	  retval=read(sockcli, str, 99);
	  str[retval-1]='\0';
	  char * pch, *user, *dest, *mesg;
	  printf ("Splitting string \"%s\" into tokens:\n",str);
	  pch = strtok (str,"|");
	  user = pch;
	  printf ("user : %s\n",user);
	  pch = strtok (NULL,"|");
	  dest = pch;
	  printf ("dest : %s\n",dest);
	  pch = strtok (NULL,"|");
	  mesg = pch;
	  printf ("message : %s\n",mesg);
	//TULIS PESAN
	char msg[20] = "Selamat datang";
	retval = write(sockcli, msg, strlen(msg));
	printf("Terkirim\n");
	close(sockcli);
	close(sockfd);
}
