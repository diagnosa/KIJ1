/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.chatapplication;

import java.net.Socket;

/**
 *
 * @author Diagnosa
 */


public class threadBaca extends Thread{
    private Socket server;
    private String mesg;
    private int connStatus;
    public threadBaca(Socket server, int connect){
        this.connStatus = connect;
        this.server = server;
    }
    
    @Override
    public void run(){
        while(connStatus==1){
           
        }
    }
}
