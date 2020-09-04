package com.d0rj.windows;

import com.d0rj.AnimationFrames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.*;


public class MyEditor extends JFrame implements KeyListener {

    private JLabel imageLabel;

    private static final String MAIN_TITLE = "W&R";
    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int DEFAULT_ICON_SIZE = 50;
    private static final int DEFAULT_TIMEOUT = 100;

    private int types = 0;
    private boolean isRunningDeer = false;

    private AnimationFrames walkAnimation;


    private URL loadResource(String path) {
        return this.getClass().getResource("/resources/" + path);
    }

    private ImageIcon rescaleIcon(ImageIcon original) {
        return new ImageIcon(
                original.getImage().getScaledInstance(DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE, Image.SCALE_FAST)
        );
    }

    private void initAnimations() {
        walkAnimation = new AnimationFrames(
                new ImageIcon[]{
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_1.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_2.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_3.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_4.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_5.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_6.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_7.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_8.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_9.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_10.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_11.png"))),
                        rescaleIcon(new ImageIcon(loadResource("images/deer_walk_12.png"))),
                }
        );
    }

    public MyEditor() {
        super(MAIN_TITLE);

        UIManager.put("TextPane.font", new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));

        initAnimations();

        var typesCounter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageLabel.setIcon(walkAnimation.getNextFrame());
                types = 0;
            }
        };
        var timer = new Timer(DEFAULT_TIMEOUT, typesCounter);
        timer.start();
    }

    public void run() {
        var editor = new JTextPane();
        imageLabel = new JLabel();
        JScrollPane editorScrollPane = new JScrollPane(editor);

        editor.setDocument(new DefaultStyledDocument());
        editor.addKeyListener(this);

        ImageIcon ii = new ImageIcon(loadResource("images/deer_walk_1.png"));
        imageLabel.setIcon(rescaleIcon(ii));

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