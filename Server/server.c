// how to compile
// gcc -o <nama_hasil_ekseskusi> <file_name>.c
// contoh: gcc -o server_result server.c
// ./<nama hasil eksekusi> (untuk run)
// contoh: ./server_result

// #include<stdio.h>
// #include<arpa/inet.h>
// #include<sys/socket.h>
// #include<errno.h>
// #include<string.h>
// #include<stdlib.h>

// struct datacli
// {
// 	int sockcli;
// 	char *user;
// };


// void main(int argc, char** argv){
// 	int sockfd, sockcli;
// 	int retval, clisize=0;
// 	struct sockaddr_in servaddr, cliaddr;

// 	sockfd = socket(AF_INET, SOCK_STREAM, 0); //hanya untk proses listen
// 	servaddr.sin_family = AF_INET;
// 	servaddr.sin_port = htons(6666);
// 	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);

// 	retval= bind(sockfd, (struct sockaddr *)&servaddr, sizeof(servaddr));
// 	if(retval < 0){
// 		perror(strerror(errno));
// 		exit(1);
// 	}
// 	printf("Server mengikat port 6666\n");
// 	retval = listen(sockfd, 5);
// 	printf("Server menunggu panggilan....\n");

// 	bzero(&cliaddr, sizeof(cliaddr));
// 	clisize= 0;
// 	sockcli = accept(sockfd, (struct sockaddr *)&cliaddr, &clisize);
// 	printf("ada panggilan");
// 	if(sockcli<0){
// 		perror(strerror(errno));
// 		exit(-1);
// 	}
// 	//BACA PESAN
// 	  char str[] ="icha|sasa|lalalalala";
// 	  bzero(str, 100);
// 	  retval=read(sockcli, str, 99);
// 	  str[retval-1]='\0';
// 	  char * pch, *user, *dest, *mesg;
// 	  pch = strtok (str,"|");
// 	  user = pch;
// 	  pch = strtok (NULL,"|");
// 	  dest = pch;
// 	  pch = strtok (NULL,"|");
// 	  mesg = pch;
// 	//TULIS PESAN
// 	char msg[20] = "Selamat datang";
// 	retval = write(sockcli, msg, strlen(msg));
// 	printf("Terkirim\n");
// 	close(sockcli);
// 	close(sockfd);
// }


#include <stdio.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>

int main( int argc, char *argv[] )
{
   int sockfd, newsockfd, portno, clilen;
   char buffer[256];
   struct sockaddr_in serv_addr, cli_addr;
   int  n;
   
   /* First call to socket() function */
   sockfd = socket(AF_INET, SOCK_STREAM, 0);
   
   if (sockfd < 0)
      {
      perror("ERROR opening socket");
      exit(1);
      }
   
   /* Initialize socket structure */
   bzero((char *) &serv_addr, sizeof(serv_addr));
   portno = 6666;
   
   serv_addr.sin_family = AF_INET;
   serv_addr.sin_addr.s_addr = INADDR_ANY;
   serv_addr.sin_port = htons(portno);
   
   /* Now bind the host address using bind() call.*/
   if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
      {
      perror("ERROR on binding");
      exit(1);
      }
      
   /* Now start listening for the clients, here process will
   * go in sleep mode and will wait for the incoming connection
   */
   
   listen(sockfd,5);
   clilen = sizeof(cli_addr);
   
   /* Accept actual connection from the client */
   newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen);
   if (newsockfd < 0)
      {
      perror("ERROR on accept");
      exit(1);
      }
   
   /* If connection is established then start communicating */
   bzero(buffer,256);
   n = read( newsockfd,buffer,255 );
   
   if (n < 0)
      {
      perror("ERROR reading from socket");
      exit(1);
      }
   
   printf("Here is the message: %s\n",buffer);
   
   /* Write a response to the client */
   n = write(newsockfd,"I got your message",18);
   
   if (n < 0)
      {
      perror("ERROR writing to socket");
      exit(1);
      }
      
   return 0;
}