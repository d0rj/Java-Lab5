package com.d0rj.windows;

import com.d0rj.DeerState;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;


public class MyEditor extends JFrame implements KeyListener {

    private JTextPane editor;
    private final DeerWidget deerWidget;
    private UndoManager undoManager;

    private static final String MAIN_TITLE = "W&R";
    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int DEFAULT_ICON_SIZE = 50;
    private static final int DEFAULT_TIMEOUT = 100;
    private static final int DEER_WALK_MARK = 1;
    private static final int DEER_RUN_MARK = 4;
    private static final List<String> FONT_LIST = Arrays.asList(new String [] {"Arial", "Calibri", "Cambria", "Courier New", "Comic Sans MS", "Dialog", "Georgia", "Helevetica", "Lucida Sans", "Monospaced", "Tahoma", "Times New Roman", "Verdana"});
    private static final String [] FONT_SIZES  = {"Font Size", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"};

    private int types = 0;
    private int ticks = 0;


    private StyledDocument getNewDocument() {
        var document = new DefaultStyledDocument();
        document.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {

            }
        });
        return document;
    }


    private Vector<String> getEditorFonts() {

        String [] availableFonts =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Vector<String> returnList = new Vector<>();

        for (String font : availableFonts) {
            if (FONT_LIST.contains(font)) {
                returnList.add(font);
            }
        }

        return returnList;
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
        editor = new JTextPane();
        var editorScrollPane = new JScrollPane(editor);

        undoManager = new UndoManager();
        var editButtonActionListener = new EditButtonActionListener();

        editor.setDocument(new DefaultStyledDocument());
        editor.addKeyListener(this);
        editor.setStyledDocument(getNewDocument());

        JButton colorButton = new JButton("Set Color");
        colorButton.addActionListener(new ColorActionListener());

        JButton boldButton = new JButton(new StyledEditorKit.BoldAction());
        boldButton.setHideActionText(true);
        boldButton.setText("Bold");
        boldButton.addActionListener(editButtonActionListener);
        JButton italicButton = new JButton(new StyledEditorKit.ItalicAction());
        italicButton.setHideActionText(true);
        italicButton.setText("Italic");
        italicButton.addActionListener(editButtonActionListener);
        JButton underlineButton = new JButton(new StyledEditorKit.UnderlineAction());
        underlineButton.setHideActionText(true);
        underlineButton.setText("Underline");
        underlineButton.addActionListener(editButtonActionListener);

        var toolBar = new JToolBar("Tools");
        toolBar.add(deerWidget);
        toolBar.add(colorButton);
        toolBar.add(boldButton);
        toolBar.add(italicButton);
        toolBar.add(underlineButton);

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


    private class EditButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editor.requestFocusInWindow();
        }
    }


    private class ColorActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Color newColor = JColorChooser.showDialog(MyEditor.this, "Choose a color", Color.BLACK);
            if (newColor == null) {
                editor.requestFocusInWindow();
                return;
            }

            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, newColor);
            editor.setCharacterAttributes(attr, false);
            editor.requestFocusInWindow();
        }
    }
}