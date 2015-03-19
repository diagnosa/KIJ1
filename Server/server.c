// how to compile
// gcc -o <nama_hasil_ekseskusi> <file_name>.c
// contoh: gcc -o server_result server.c
// ./<nama hasil eksekusi> (untuk run)
// contoh: ./server_result

#include <stdio.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

#define PORTNO 7777

typedef struct Client
{
   char user_id[128];
   int cli_sockfd;
   struct Client *Next;
   struct Client *Prev;
}Client;

Client *first;
Client *last;

char *getMode(char messageCome[], int size)
{
   
}

void broadcast_list_user()
{

}

// Client to Server:
// - set username:
// -> set_username|user_name
// - send message:
// -> send|tujuan|isi_pesan
// Server to Client:
// - List User Online
// -> list|user1|user2|....
// - send message:
// -> message|dari|isi_pesan

void* myThread(Client *mc)
{
   Client *myClient = (Client*) mc;
   Client *iterator;

   char messageCome[8192];
   char messageGo[8192];
   char *mode;
   char *from;
   char *content;

   int sizeMessage;
   int bar1, bar2;

   while((sizeMessage = read(myClient->cli_sockfd, messageCome, sizeof(messageCome) - 1)) > 0)
   {
      bar1 = bar2 = 0;
      messageCome[sizeMessage] = '\0';

      int i;
      for (i = 0; i < sizeMessage; i++)
      {
         if(messageCome[i] == '|')
         {
            bar1 = i + 1;
            break;
         }

         mode[i] = messageCome[i];
      }
      mode[i] = '\0';
   }

}

int main( int argc, char *argv[] )
{
   int sockfd, newsockfd, clilen;
   char buffer[256];
   struct sockaddr_in serv_addr, cli_addr;
   int  n;
   pthread_t ThreadClient;
   
   /* panggil fungsi socket */
   sockfd = socket(AF_INET, SOCK_STREAM, 0);
   if (sockfd < 0)
   {
      perror("ERROR opening socket");
      exit(1);
   }
   
   /* inisialisasi struktur socket */
   bzero((char *) &serv_addr, sizeof(serv_addr));
   
   serv_addr.sin_family = AF_INET;
   serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
   serv_addr.sin_port = htons(PORTNO);
   
   if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
   {
      perror("ERROR on binding");
      exit(1);
   }  
   
   if (listen(sockfd, 7) < 0)
   {
      perror("ERROR on listen");
      exit(1);
   }
   clilen = sizeof(cli_addr);
   
   while(1)
   {
      // terima koneksi dari client
      newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen);
      if (newsockfd < 0)
      {
         perror("ERROR on accept");
         exit(1);
      }
      else
      {
         printf("New client has connected\n");
      }

      // buat struct clientnya
      Client *newCli = (Client*) malloc(sizeof(Client));
      newCli->cli_sockfd = newsockfd;

      // atur linked-listnya
      if(first == NULL)
      {
         first = last = newCli;
         newCli->Prev = NULL;
      }
      else
      {
         last->Next = newCli;
         newCli->Prev = last;
         last = newCli;
      }
      newCli->Next = NULL;

      // buat threadnya
      pthread_create(&ThreadClient, NULL, myThread, newCli);
   }
   
   /* If connection is established then start communicating */
 //   bzero(buffer,256);
 //   n = read( newsockfd,buffer,255 );
   
 //   if (n < 0)
 //      {
 //      perror("ERROR reading from socket");
 //      exit(1);
 //      }
   
 //   printf("n: %d\n", n);
 //   printf("Here is the message: %s\n", buffer);
	// int i;
 //   for (i=0; i<n; i++) {
 //   	printf("%d ", buffer[i]);
 //   }

 //   /* Write a response to the client */
 //   n = write(newsockfd,"I got your message",18);
   
 //   if (n < 0)
 //      {
 //      perror("ERROR writing to socket");
 //      exit(1);
 //      }
      
   return 0;
}

// #include<stdio.h>
// #include<arpa/inet.h>
// #include<sys/socket.h>
// #include<errno.h>
// #include<string.h>
// #include<stdlib.h>

// struct datacli
// {
//    int sockcli;
//    char *user;
// };


// void main(int argc, char** argv){
//    int sockfd, sockcli;
//    int retval, clisize=0;
//    struct sockaddr_in servaddr, cliaddr;

//    sockfd = socket(AF_INET, SOCK_STREAM, 0); //hanya untk proses listen
//    servaddr.sin_family = AF_INET;
//    servaddr.sin_port = htons(6666);
//    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);

//    retval= bind(sockfd, (struct sockaddr *)&servaddr, sizeof(servaddr));
//    if(retval < 0){
//       perror(strerror(errno));
//       exit(1);
//    }
//    printf("Server mengikat port 6666\n");
//    retval = listen(sockfd, 5);
//    printf("Server menunggu panggilan....\n");

//    bzero(&cliaddr, sizeof(cliaddr));
//    clisize= 0;
//    sockcli = accept(sockfd, (struct sockaddr *)&cliaddr, &clisize);
//    printf("ada panggilan");
//    if(sockcli<0){
//       perror(strerror(errno));
//       exit(-1);
//    }
//    //BACA PESAN
//      char str[] ="icha|sasa|lalalalala";
//      bzero(str, 100);
//      retval=read(sockcli, str, 99);
//      str[retval-1]='\0';
//      char * pch, *user, *dest, *mesg;
//      pch = strtok (str,"|");
//      user = pch;
//      pch = strtok (NULL,"|");
//      dest = pch;
//      pch = strtok (NULL,"|");
//      mesg = pch;
//    //TULIS PESAN
//    char msg[20] = "Selamat datang";
//    retval = write(sockcli, msg, strlen(msg));
//    printf("Terkirim\n");
//    close(sockcli);
//    close(sockfd);
// }