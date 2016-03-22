package com.company;

//imported libraries
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Server extends JFrame implements ActionListener
{
    //declare variables
    static ServerSocket server;
    static Socket conn;
    JPanel panel;
    JTextField newMsg;
    JTextArea chatHistory;
    JButton send;
    DataInputStream dis;
    DataOutputStream dos;

    public Server() throws UnknownHostException, IOException {

        panel = new JPanel();
        newMsg = new JTextField();
        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        send = new JButton("Send");

        this.setSize(500, 500);
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setLayout(null);
        this.add(panel);

        chatHistory.setBounds(20, 20, 450, 360);
        panel.add(chatHistory);

        newMsg.setBounds(20, 400, 340, 30);
        panel.add(newMsg);

        send.setBounds(375, 400, 95, 30);
        panel.add(send);

        this.setTitle("Server ");
        send.addActionListener(this);
        newMsg.addActionListener(this);
        newMsg.requestFocusInWindow();

        server = new ServerSocket(2000, 1, InetAddress.getLocalHost());
        chatHistory.setText("\nWaiting for Client ");
        conn = server.accept();
        chatHistory.setText(chatHistory.getText() + "\nClient Found");

        while (true) {
            try {
                DataInputStream dis = new DataInputStream(conn.getInputStream());
                String string = dis.readUTF();
                chatHistory.setText(chatHistory.getText() + "\nClient: " + string);
            }//end try
            catch (Exception e1) {
                chatHistory.setText(chatHistory.getText() + "\nMessage sending fail:Network Error");
                try {
                    Thread.sleep(3000);
                    System.exit(0);
                } //end inner try
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }//end inner catch
            }//end catch
        }//end while
    }//end Server()

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (! newMsg.getText().trim().isEmpty() ) {
//        if (newMsg.getText() != NULL) {
            chatHistory.setText(chatHistory.getText() + "\nMe:" + newMsg.getText());
            try {
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeUTF(newMsg.getText());
            } //end try
            catch (Exception e1) {
                chatHistory.setText(chatHistory.getText() + "\nMessage sending fail: Network Error");
                try {
                    Thread.sleep(3000);
                    System.exit(0);
                }//end inner try
                catch (InterruptedException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }//end inner catch
            }//end catch
            newMsg.setText("");
        }//end if
    }//end ActionPerformed()

    public static void main(String[] args) throws UnknownHostException,IOException
    {
        new Server();
    }//end main()
}//end class()
