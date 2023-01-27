package org.example.GUI;

import org.example.tests.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterUsername implements Runnable  {

    private JPanel panel;
    private JFrame frame;
    private JPanel inputPanel;
    private JTextField textField;
    private String text;

    public EnterUsername(String str) {
        this.text = str;
    }



    @Override
    public void run() {
        frame = new JFrame("Choix username");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel(text);
        panel.add(label, BorderLayout.NORTH);

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        textField = new JTextField(20);
        inputPanel.add(textField);

        JButton button = new JButton("Valider");
        inputPanel.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!textField.getText().equals("")){

                }
            }
        });

        panel.add(inputPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }
}
