/*
 * Server.java
 * Author : Arakene
 * Created Date : 2020-01-09
 */
package chatting.graphics;

import chatting.communication.Server_gui;
import chatting.core.Attachment;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Server {
    private JFrame mainFrame = new JFrame();
    private JPanel contentPanel = new JPanel();
    private JTextArea textArea = new JTextArea();
    private JTextField textField = new JTextField();
    private Server_gui server;
    private JLabel label = new JLabel();


    public Server() {
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        contentPanel.setBackground(Color.BLACK);
//        contentPanel.add(textArea, BorderLayout.CENTER);
        contentPanel.add(textField, BorderLayout.SOUTH);

        label.setOpaque(false);
        contentPanel.add(label, BorderLayout.CENTER);

        mainFrame.add(contentPanel);

        textField.addActionListener(event -> sendToAll());


        server = new Server_gui(this::displayDataFromClient,this::displayImage);
        server.startAccept();


        mainFrame.setTitle("Server");
        mainFrame.setSize(600, 600);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
    public void displayDataFromClient(String msg){
        textArea.append("Client : "+msg+"\r\n");
    }
    public void sendToAll(){
        String msg = textField.getText();
        textField.setText("");
        server.sentToAllClient(msg);
    }
    public void displayImage(ImageIcon image){
        label.setIcon(image);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
