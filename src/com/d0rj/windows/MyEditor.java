package com.d0rj.windows;

import com.d0rj.AnimationFrames;
import com.d0rj.DeerState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.*;


public class MyEditor extends JFrame implements KeyListener {

    private DeerWidget deerWidget;

    private static final String MAIN_TITLE = "W&R";
    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int DEFAULT_ICON_SIZE = 50;
    private static final int DEFAULT_TIMEOUT = 100;
    private static final int DEER_WALK_MARK = 1;
    private static final int DEER_RUN_MARK = 4;

    private int types = 0;
    private int ticks = 0;

    private AnimationFrames walkAnimation;
    private AnimationFrames runAnimation;


    private URL loadResource(String path) {
        return this.getClass().getResource("/resources/" + path);
    }



    public MyEditor() {
        super(MAIN_TITLE);

        UIManager.put("TextPane.font", new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));
        deerWidget = new DeerWidget(DEFAULT_ICON_SIZE);

        var typesCounter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (types >= DEER_RUN_MARK)
                    deerWidget.setState(DeerState.Run);
                else if (types >= DEER_WALK_MARK)
                    deerWidget.setState(DeerState.Walk);
                else
                    deerWidget.setState(DeerState.Stand);

                deerWidget.nextFrame();

                if (ticks % (1000 / DEFAULT_TIMEOUT) == 0)
                    types = 0;
                ++ticks;
            }
        };
        var timer = new Timer(DEFAULT_TIMEOUT, typesCounter);
        timer.start();
    }

    public void run() {
        var editor = new JTextPane();
        JScrollPane editorScrollPane = new JScrollPane(editor);

        editor.setDocument(new DefaultStyledDocument());
        editor.addKeyListener(this);

        var toolBar = new JToolBar("Tools");
        toolBar.add(deerWidget);

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