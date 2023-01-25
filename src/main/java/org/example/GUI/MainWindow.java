package org.example.GUI;

import org.example.NetworkManager.NetworkManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class MainWindow {

    private JTextField messageField;
    private JButton sendButton;
    private DefaultListModel<String> messagesModel;
    private JList<String> messagesList;
    private DefaultListModel<String> listModel;
    private JList<String> list;
    private MessageRenderer renderer;
    private int counter = 0;
    private JPanel top, bottom, right, left, center;
    private JFrame frame;
    private JLabel topLabel;
    private LinkedList<String> listConnectedUsersDisplayed;

    private String currentConversation;

    /////////////////////////////////////////
    ////////// Create GUI skeleton //////////
    /////////////////////////////////////////
    public MainWindow(String wd){
        this.listConnectedUsersDisplayed = new LinkedList<>();
        this.listConnectedUsersDisplayed.add(wd); // Empêche au programme de s'afficher dans la liste des utilisateurs connectés
        this.frame = new JFrame(wd);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.frame.setUndecorated(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        System.out.println(width + " " + height);

        ////////////////////////////////////////
        ////////// Container creation //////////
        ////////////////////////////////////////

        topLabel = new JLabel("");
        this.top = new JPanel();
        this.top.setPreferredSize(new Dimension(300, 70));
        this.top.add(topLabel);
        this.frame.getContentPane().add(this.top, BorderLayout.NORTH);

        this.left = new JPanel();
        this.left.setPreferredSize(new Dimension(300, 3000));
        this.left.setLayout(new FlowLayout());
        //JScrollPane leftScrollPane = new JScrollPane(left);
        //leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //this.frame.add(leftScrollPane, BorderLayout.WEST);
        this.frame.add(this.left, BorderLayout.WEST);

        this.center = new JPanel(new BorderLayout());
        this.center.setPreferredSize(new Dimension(150, 100));
        this.center.setBackground(Color.cyan);
        this.frame.getContentPane().add(this.center, BorderLayout.CENTER);

        this.right = new JPanel();
        this.right.setPreferredSize(new Dimension(300, 3000));
        this.right.setLayout(new FlowLayout());
        //JScrollPane rightScrollPane = new JScrollPane(right);
        //rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //this.frame.add(rightScrollPane, BorderLayout.EAST);
        this.frame.add(this.right, BorderLayout.EAST);

        this.bottom = new JPanel();
        this.bottom.setPreferredSize(new Dimension(300, 50));
        //bottom.setBackground(Color.red);
        this.frame.getContentPane().add(this.bottom, BorderLayout.SOUTH);

        /////////////////////////////////////////
        /////////// Message displayer ///////////
        /////////////////////////////////////////

        this.messageField = new JTextField(100);
        this.sendButton = new JButton("Send");
        this.messagesModel = new DefaultListModel<>();
        this.messagesList = new JList<>(this.messagesModel);

        JScrollPane scrollPane = new JScrollPane(this.messagesList);

        JPanel inputPanel = new JPanel();
        inputPanel.add(this.messageField);
        inputPanel.add(this.sendButton);

        this.bottom.add(inputPanel);
        this.center.add(scrollPane);

        this.listModel = new DefaultListModel<>();
        this.list = new JList<>(this.listModel);
        this.renderer = new MessageRenderer();
        this.list.setCellRenderer(this.renderer);
        this.center.add(this.list);

        // Add an action listener to the send button
        this.sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO
                // Clear the text field
                String msg = messageField.getText();
                messageField.setText("");

                NetworkManager.sendMessage(msg, topLabel.getText());

            }
        });

        /////////////////////////////////////////
        /////////// Action on Closure ///////////
        /////////////////////////////////////////

        // Show the Frame
        this.frame.pack();
        this.frame.setVisible(true);

       /* this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(this.frame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    //TODO send broadcast
                    System.exit(0);
                }
            }
        });*/
    }

    public void displayMessage(String message, String date, int place){
        // center : 4 for right, 2 for left

        System.out.println(message + date + place);
        if (place == 2){
            listModel.addElement(topLabel.getText() + message);
        } else {
            listModel.addElement("YOU: " + message);
        }

        renderer.setParamsText(counter++, place);

        listModel.addElement(date);
        renderer.setParamsText(counter++, place, 8, Color.GRAY);

        center.revalidate();
    }

    //////////////////////////////////////////////////////
    /////////// Create button for conversation ///////////
    //////////////////////////////////////////////////////
    public void addConversations(String name){
        // Création des composants de l'interface
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(300, 40));
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                //TODO
                String tmp = e.getActionCommand();
                topLabel.setText(tmp);
                currentConversation = tmp;
                listModel.removeAllElements();
                center.revalidate();
                NetworkManager.getMessages(currentConversation);
            }
        });
        this.left.add(button);
        this.right.revalidate();
    }

    public void addConnectedUser(String name){
        // Création des composants de l'interface
        if(!this.listConnectedUsersDisplayed.contains(name)){
            JButton button = new JButton(name);
            button.setPreferredSize(new Dimension(300, 40));
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e)
                {
                    topLabel.setText(e.getActionCommand());
                }
            });
            this.right.add(button);
            this.right.revalidate();
            this.listConnectedUsersDisplayed.add(name);
        }
    }

    public String getDest(){
        return this.topLabel.getText();
    }

}
