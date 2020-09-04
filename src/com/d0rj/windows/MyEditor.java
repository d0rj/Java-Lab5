package com.d0rj.windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.text.*;


public class MyEditor extends JFrame implements KeyListener {

    private static final String MAIN_TITLE = "W&R";
    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int DEFAULT_ICON_SIZE = 40;
    private static final int DEFAULT_TIMEOUT = 200;

    private int types = 0;


    public MyEditor() {
        super(MAIN_TITLE);

        UIManager.put("TextPane.font", new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));

        var typesCounter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                types = 0;
            }
        };
        var timer = new Timer(DEFAULT_TIMEOUT, typesCounter);
        timer.start();
    }

    public void run() {
        var editor = new JTextPane();
        var imageLabel = new JLabel();
        JScrollPane editorScrollPane = new JScrollPane(editor);

        editor.setDocument(new DefaultStyledDocument());
        editor.addKeyListener(this);

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/resources/images/deer_walk_1.png"));
        var image = ii.getImage().getScaledInstance(DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE, Image.SCALE_FAST);
        imageLabel.setIcon(new ImageIcon(image));

        var toolBar = new JToolBar("Tools");
        toolBar.add(imageLabel);

        add(editorScrollPane, BorderLayout.CENTER);
        add(toolBar, BorderLayout.PAGE_START);
        setSize(900, 500);
        setLocation(150, 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        editor.requestFocusInWindow();
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