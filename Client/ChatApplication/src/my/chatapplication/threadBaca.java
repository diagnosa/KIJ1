/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.chatapplication;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author Diagnosa
 */


public class threadBaca extends Thread{
    private Socket server;
    private String mesg;
    private String command;
    private int connStatus;
    private BufferedInputStream bis;
    private List listOnline ;
    private JTextArea messageLabel ;
    public threadBaca(Socket sockcli, int connect, List listOnline, JTextArea messageLabel){
        this.connStatus = connect;
        this.server = sockcli;
        this.listOnline = listOnline;
        this.messageLabel = messageLabel;
    }
    
    @Override
    public void run(){
        while(connStatus==1){
            try {
                bis = new BufferedInputStream(server.getInputStream());
                this.mesg = bis.toString();
                String[] isi = this.mesg.split("|");
                if("list".equals(isi[0])){
                    for(int i=1; i<isi.length ;i++){
                        this.listOnline.add(isi[i]);
                    }
                }    
                else this.messageLabel.append(isi[1] + " : " + isi[2]);
            } catch (IOException ex) {
                this.messageLabel.append("failed to receive from server");
            }
           
        }
    }
}
