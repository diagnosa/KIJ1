/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.chatapplication;

import java.awt.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
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
    private BufferedReader in;
    private DataInputStream dis;
    private List listOnline;
    private JTextArea messageLabel;
    private byte[] messageByte = new byte[1000];
    private StringBuffer sb = new StringBuffer();
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
            System.out.println(this.connStatus);
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            while (connStatus == 1) {
            //dis = new DataInputStream(server.getInputStream());
                //this.mesg = dis.readLine();
//                if((bytesRead = dis.read(messageByte)) > 0)
//                {
                    System.out.println("Masuk IF");
                    //System.out.println(bytesRead);
                    //mesg = new String(messageByte, 0, bytesRead);
                    do {
                        char[] c = new char[] { 1024 };
                        in.read(c);
                        sb.append(c);
                        }while (in.ready());
                    this.mesg = sb.toString();
                    sb.delete(0, sb.length());
                    //bytesRead = 0;
//                    this.mesg = in.readLine().trim();
                    System.out.println(this.mesg);
                    //String[] isi = this.mesg.split("|");
                    ArrayList<String> isi = getContent(this.mesg);
                    System.out.println(isi.size());
                    if (isi != null && "list".equals(isi.get(0))) {
                        this.listOnline.removeAll();
                        for (int i = 1; i < isi.size(); i++) {
                            System.out.println(isi.get(i));
                            this.listOnline.add(isi.get(i));
                        }
                    } else if (isi != null && "message".equals(isi.get(0))){
                        this.messageLabel.append(isi.get(1) + " : " + isi.get(2) + "\n");
                    }
//                }
            }
        } catch (IOException ex) {
            this.messageLabel.append("failed to receive from server");
        }
        
        finally
        {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(threadBaca.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private ArrayList getContent(String mesg)
    {
        ArrayList<String> content = new ArrayList<String>();
        String temp = "";
        int size = mesg.length();
        for(int i = 0; i < size; i++)
        {
            if(mesg.charAt(i) == '|')
            {
                System.out.println(temp);
                content.add(temp);
                temp = "";
                continue;
            }
            
            temp  += mesg.charAt(i);
        }
        content.add(temp);
        return content;
    }
}
