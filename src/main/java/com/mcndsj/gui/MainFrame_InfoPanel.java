package com.mcndsj.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Matthew on 21/06/2016.
 */
public class MainFrame_InfoPanel  extends JPanel{


    private JTextPane textArea;
    private SimpleDateFormat format =   new SimpleDateFormat("hh:mm:ss");

    public MainFrame_InfoPanel(MainFrame frame){
        textArea = new JTextPane();
        textArea.setPreferredSize(new Dimension(800,180));
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        add(scroll);
    }

    public void println(String s,Color color){
        appendToPane(textArea,"[" +format.format(Calendar.getInstance().getTime()) + "] "+ s +  "\n",color);
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        DefaultStyledDocument document = (DefaultStyledDocument)tp.getDocument();
        try {
            document.insertString(len,msg,aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
