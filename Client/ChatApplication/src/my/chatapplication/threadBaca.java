/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.chatapplication;

import java.awt.List;
import java.io.DataInputStream;
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
public class threadBaca extends Thread {

    private Socket server;
    private String mesg;
    private String command;
    private int connStatus;
    private DataInputStream dis;
    private List listOnline;
    private JTextArea messageLabel;
    private byte[] messageByte = new byte[1000];
    private int bytesRead;

    public threadBaca(Socket sockcli, int connect, List listOnline, JTextArea messageLabel) {
        this.connStatus = connect;
        this.server = sockcli;
        this.listOnline = listOnline;
        this.messageLabel = messageLabel;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(server.getInputStream());
            System.out.println(this.connStatus);
            while (connStatus == 1) {
                System.out.println("64564");
                //this.mesg = dis.readLine();
                bytesRead = dis.read(messageByte);
                mesg = new String(messageByte, 0, bytesRead);
                System.out.println(this.mesg);
                //String[] isi = this.mesg.split("|");
                String[] isi = getContent(this.mesg);
                if ("list".equals(isi[0])) {
                    for (int i = 1; i < isi.length; i++) {
                        this.listOnline.add(isi[i]);
                    }
                } else {
                    this.messageLabel.append(isi[1] + " : " + isi[2]);
                }

            }
        } catch (IOException ex) {
            this.messageLabel.append("failed to receive from server");
        }
    }
    
    private String[] getContent(String mesg)
    {
        String[] content = new String[100];
        int size = mesg.length();
        int j = 0;
        content[j] = "";
        for(int i = 0; i < size; i++)
        {
            if(mesg.charAt(i) == '|')
            {
                j++;
                content[j] = "";
                continue;
            }
            
            content[j] += mesg.charAt(i);
        }
        return content;
    }
}
