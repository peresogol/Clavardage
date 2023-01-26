package org.example.utils;

import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class AlertWindow {

    public AlertWindow(String str) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Attention");
        JOptionPane.showMessageDialog(dialog, str);
    }

}
