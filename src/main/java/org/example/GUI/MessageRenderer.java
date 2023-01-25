package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MessageRenderer extends DefaultListCellRenderer {
    private Map<Integer, Integer> alignments;
    private Map<Integer, Integer> fontSizes;
    private Map<Integer, Color> fontColors;


    public MessageRenderer() {
        fontColors = new HashMap<>();
        fontSizes = new HashMap<>();
        alignments = new HashMap<>();
    }

    public void setParamsText(int index, int alignment, int fontSize, Color fontColor) {
        alignments.put(index, alignment);
        fontSizes.put(index, fontSize);
        fontColors.put(index, fontColor);
    }

    public void setParamsText(int index, int alignment) {
        alignments.put(index, alignment);
    }

    /*
    Permet l'affichage sous forme de conversation dans le panel central, ainsi que la sélection d'une couleur et taille de police, et d'un centrage sur
    la droite ou sur la gauche.
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Integer alignment = alignments.get(index);
        if (alignment != null) {
            ((JLabel) c).setHorizontalAlignment(alignment);
        }

        Integer fontSize = fontSizes.get(index);
        if (fontSize != null)   {
            ((JLabel) c).setFont(new Font(((JLabel) c).getFont().getName(), ((JLabel) c).getFont().getStyle(), fontSize));
        }

        Color fontColor = fontColors.get(index);
        if (fontColor != null) {
            ((JLabel) c).setForeground(fontColor);
        }

        return c;
    }

}