package com.d0rj.windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.text.*;


public class MyEditor implements KeyListener {

    private JFrame frame__;
    private JTextPane editor__;
    private JLabel imageLabel = new JLabel();

    private static final String MAIN_TITLE = "W&R";
    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 14;

    private static int types = 0;
    private static int DEFAULT_ICON_SIZE = 40;

    public static void main(String [] args) throws Exception {
        UIManager.put("TextPane.font", new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));

        var typesCounter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                types = 0;
            }
        };
        var timer = new Timer(100, typesCounter);
        timer.start();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MyEditor().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {

        frame__ = new JFrame(MAIN_TITLE);
        editor__ = new JTextPane();
        JScrollPane editorScrollPane = new JScrollPane(editor__);

        editor__.setDocument(new DefaultStyledDocument());
        editor__.addKeyListener(this);

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/resources/images/deer_walk_1.png"));
        var image = ii.getImage().getScaledInstance(DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE, Image.SCALE_FAST);
        imageLabel.setIcon(new ImageIcon(image));

        JToolBar toolBar = new JToolBar("Tools");
        toolBar.add(imageLabel);

        frame__.add(editorScrollPane, BorderLayout.CENTER);
        frame__.add(toolBar, BorderLayout.PAGE_START);
        frame__.setSize(900, 500);
        frame__.setLocation(150, 80);
        frame__.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame__.setVisible(true);

        editor__.requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        ++types;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}