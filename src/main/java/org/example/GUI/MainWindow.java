package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainWindow {

    private static JTextField messageField;
    private static JButton sendButton;
    private static DefaultListModel<String> messagesModel;
    private static JList<String> messagesList;
    private static DefaultListModel<String> listModel;
    private static JList<String> list;
    private static MessageRenderer renderer;
    private static int counter = 0;
    private static JPanel top, bottom, right, left, center;
    private static JFrame frame;

    //////////////////////////////////////////////////////
    /////////// Create button for conversation ///////////
    //////////////////////////////////////////////////////
    public static void addConversations(String name){
        // Création des composants de l'interface
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(300, 40));
        left.add(button);
        //TODO ajouter au tableau
    }

    public static void addConnectedUser(String name){
        // Création des composants de l'interface
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(300, 40));
        right.add(button);
        //TODO ajouter au tableau
    }

    /////////////////////////////////////////
    ////////// Create GUI skeleton //////////
    /////////////////////////////////////////
    public static void createAndShowGUI(){
        frame = new JFrame("Layout Demo");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        System.out.println(width + " " + height);

        ////////////////////////////////////////
        ////////// Container creation //////////
        ////////////////////////////////////////

        top = new JPanel();
        top.setPreferredSize(new Dimension(300, 70));
        frame.getContentPane().add(top, BorderLayout.NORTH);

        left = new JPanel();
        left.setPreferredSize(new Dimension(300, 3000));
        left.setLayout(new FlowLayout());
        //JScrollPane leftScrollPane = new JScrollPane(left);
        //leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //frame.add(leftScrollPane, BorderLayout.WEST);
        frame.add(left, BorderLayout.WEST);

        center = new JPanel(new BorderLayout());
        center.setPreferredSize(new Dimension(150, 100));
        center.setBackground(Color.cyan);
        frame.getContentPane().add(center, BorderLayout.CENTER);

        right = new JPanel();
        right.setPreferredSize(new Dimension(300, 3000));
        right.setLayout(new FlowLayout());
        //JScrollPane rightScrollPane = new JScrollPane(right);
        //rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //frame.add(rightScrollPane, BorderLayout.EAST);
        frame.add(right, BorderLayout.EAST);

        bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(300, 50));
        //bottom.setBackground(Color.red);
        frame.getContentPane().add(bottom, BorderLayout.SOUTH);

        /////////////////////////////////////////
        /////////// Message displayer ///////////
        /////////////////////////////////////////

        messageField = new JTextField(100);
        sendButton = new JButton("Send");
        messagesModel = new DefaultListModel<>();
        messagesList = new JList<>(messagesModel);

        JScrollPane scrollPane = new JScrollPane(messagesList);

        JPanel inputPanel = new JPanel();
        inputPanel.add(messageField);
        inputPanel.add(sendButton);

        bottom.add(inputPanel);
        center.add(scrollPane);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        renderer = new MessageRenderer();
        list.setCellRenderer(renderer);
        center.add(list);

        // Add an action listener to the send button
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO
                // Clear the text field
                String msg = messageField.getText();
                messageField.setText("");
                displayMessage(msg, 4); // 4 means right-centered

            }
        });

        /////////////////////////////////////////
        /////////// Action on Closure ///////////
        /////////////////////////////////////////

        // Show the Frame
        frame.pack();
        frame.setVisible(true);

       /* frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    //TODO send broadcast
                    System.exit(0);
                }
            }
        });*/
    }

    public static void loadConversation(){

    }

    public static void displayMessage(String message, int center){
        // center : 4 for right, 2 for left
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if (center == 2){
            listModel.addElement("NOM: " + message);
        } else {
            listModel.addElement("YOU: " + message);
        }

        renderer.setParamsText(counter++, center);

        listModel.addElement(dateFormat.format(date));
        renderer.setParamsText(counter++, center, 8, Color.GRAY);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                for(int i =0; i < 5; i++){
                    addConversations("aa");
                    addConversations("aa");
                }
                for(int i =0; i < 15; i++){
                    addConnectedUser("Sacha");
                    addConnectedUser("JF");
                }
            }
        });
    }
}



/*package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private JTextField messageField;
    private JButton sendButton;
    private DefaultListModel<String> messagesModel;
    private JList<String> messagesList;

    public MainWindow() {

        // Set the title and layout of the window
        setTitle("Message App");
        setLayout(new BorderLayout());

        // Initialize the text field, button, and list model
        messageField = new JTextField(20);
        sendButton = new JButton("Send");
        messagesModel = new DefaultListModel<>();

        // Add the text field and button to a panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(messageField);
        inputPanel.add(sendButton);

        // Add the panel to the window
        add(inputPanel, BorderLayout.SOUTH);

        // Create the list and add it to a scroll pane
        messagesList = new JList<>(messagesModel);
        JScrollPane scrollPane = new JScrollPane(messagesList);

        // Add the scroll pane to the window
        add(scrollPane, BorderLayout.CENTER);

        // Add an action listener to the send button
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the message from the text field
                String message = messageField.getText();

                // Clear the text field
                messageField.setText("");

                // Add the message to the list model
                messagesModel.addElement("You: " + message);
            }
        });

        // Création des composants de l'interface
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JButton button1 = new JButton("Bouton 1");
        JButton button2 = new JButton("Bouton 2");
        JButton button3 = new JButton("Bouton 3");
        leftPanel.add(button1);
        leftPanel.add(button2);
        leftPanel.add(button3);

        JScrollPane leftScrollPane = new JScrollPane(leftPanel);

        JTextArea textArea1 = new JTextArea("Champ de texte 1");
        JTextArea textArea2 = new JTextArea("Champ de texte 2");
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(textArea1);
        rightPanel.add(textArea2);

        JScrollPane rightScrollPane = new JScrollPane(rightPanel);

        // Ajout des composants à la fenêtre
      //  add(leftScrollPane, BorderLayout.WEST);
       // add(rightScrollPane, BorderLayout.EAST);


        // Set the size and location of the window
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Display the window
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
*/