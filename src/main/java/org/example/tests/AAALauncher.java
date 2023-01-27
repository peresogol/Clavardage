package org.example.tests;

import org.example.utils.FormatServiceMessage;
import org.example.Managers.ThreadManager;
import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class AAALauncher {

    private static ServerUDP serv;
    private static NetworkManager networkManager;
    private static ThreadManager threadManager;
    private static JPanel panel;
    private static JFrame frame;
    private static JPanel inputPanel;
    private static JTextField textField;
    private String text;



    public static void main(String[] args) {
        frame = new JFrame("Choix username");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Veuillez saisir un nom d'utilisateur :");
        panel.add(label, BorderLayout.NORTH);

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        textField = new JTextField(20);
        inputPanel.add(textField);

        JButton button = new JButton("Valider");
        inputPanel.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tmp = textField.getText();
                if(!tmp.equals("")){
                    threadManager = new ThreadManager(5500);
                    threadManager.start();
                    networkManager = new NetworkManager(tmp, 6000);
                    startUDPServer();
                }
            }
        });

        panel.add(inputPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }

    // Start Server UDP
    public static void startUDPServer(){
        serv = new ServerUDP(5000, packet -> networkManager.msgUDPhandler(packet));
        serv.start();
        discoverNetwork();
    }

    // Send broadcast to discover network
    public static void discoverNetwork(){
        String msg = FormatServiceMessage.msgGetConnectedUsers();
        ClientUDP.sendBroadcast(msg);
    }


}



/*package org.example.tests;

import org.example.GUI.EnterUsername;
import org.example.utils.FormatServiceMessage;
import org.example.Managers.ThreadManager;
import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;

public class AAALauncher extends Launcher {

    private static ServerUDP serv;
    public static NetworkManager networkManager;
    private static ThreadManager threadManager;

    public static void main(String[] args) {
        new EnterUsername("Veuillez saisir un nom d'utilisateur :");
    }

    public void startApp(String text) {
        threadManager = new ThreadManager(5500);
        threadManager.start();
        networkManager = new NetworkManager(text, 6000);
        startUDPServer();
    }

    // Start Server UDP
    public static void startUDPServer(){
        serv = new ServerUDP(5000, packet -> networkManager.msgUDPhandler(packet));
        serv.start();
        discoverNetwork();
    }

    // Send broadcast to discover network
    public static void discoverNetwork(){
        String msg = FormatServiceMessage.msgGetConnectedUsers();
        ClientUDP.sendBroadcast(msg);
    }
}
*/