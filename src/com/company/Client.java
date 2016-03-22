package com.company;

//imported libraries
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Client extends JFrame implements ActionListener {
    //declare variables
    static Socket conn;
    JPanel panel;
    JTextArea chatHistory;
    JTextField newMsg;
    JButton send;

    public Client() throws UnknownHostException, IOException {
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

        send.addActionListener(this);
        newMsg.addActionListener(this);
        newMsg.requestFocusInWindow();
        conn = new Socket(InetAddress.getLocalHost(), 2000);

		/*
		 * for remote pc use ip address of server with function
		 * InetAddress.getByName("\nProvide ip here")
		 * ChatHistory.setText("\nConnected to Server");
		 */

        chatHistory.setText("\nConnected to Server");
        this.setTitle("\nClient ");

        while (true) {
            try {
                DataInputStream dis = new DataInputStream(conn.getInputStream());
                String string = dis.readUTF();
                chatHistory.setText(chatHistory.getText() + "\nServer: " + string);
            }//end try
            catch (Exception e1) {
                chatHistory.setText(chatHistory.getText() + "\nMessage sending fail: Network Error");
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
    }//end Client()

    @Override
    public void actionPerformed(ActionEvent e) {

        if (! newMsg.getText().trim().isEmpty() ) {
//        if (newMsg.getText() != NULL) {
            chatHistory.setText(chatHistory.getText() + "\nMe:" + newMsg.getText());
            try {
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeUTF(newMsg.getText());
            }//end try
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

    public static void main(String[] args) throws UnknownHostException, IOException {
        new Client();
    }//end main()
}//end class()

