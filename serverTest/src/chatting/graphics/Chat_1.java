package chatting.graphics;

import chatting.communication.Client_gui;

import javax.swing.*;
import java.awt.*;

public class Chat_1 {
    private JFrame frame = new JFrame();
    private JPanel contentPanel = new JPanel();
    private JTextField textField = new JTextField();
    private JTextArea textArea = new JTextArea();
    private Client_gui client;
    private Font serverFont = new Font("궁서",Font.BOLD,20);
    private Font clientFont = new Font("고딕",Font.BOLD,15);

    public Chat_1() {
        initialize();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(textArea, BorderLayout.CENTER);
        contentPanel.add(textField, BorderLayout.SOUTH);
        textField.setFont(new Font("고딕", Font.BOLD, 15));
        textArea.setFont(new Font("고딕", Font.BOLD, 15));
        textArea.setEditable(false);
        contentPanel.setFocusable(true);

        frame.setTitle("Client1");

        textField.addActionListener(event -> userTextDisplaySend());

        frame.add(contentPanel);

        frame.setSize(700, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initialize() {
        client = new Client_gui(this::serverTextDisplay);
        client.addDisplay(this::serverTextDisplay);
        client.run();
    }

    public void userTextDisplaySend() {
        String text = textField.getText();
        client.writeToServer(text);
        textField.setText("");
        textArea.setFont(clientFont);
        textArea.append(text + "\r\n");
    }

    public void serverTextDisplay(String text) {
        textArea.setFont(serverFont);
        textArea.append(text + "\r\n");
    }

    public String getUserInput(){
        return textField.getText();
    }
}
