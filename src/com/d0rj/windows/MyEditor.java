package com.d0rj.windows;

import com.d0rj.DeerState;
import com.d0rj.widgets.DeerWidget;

import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.swing.text.*;


public class MyEditor extends JFrame implements KeyListener {

    private JTextPane editor;
    private final DeerWidget deerWidget;
    private JComboBox<String> fontFamilyComboBox;
    private JComboBox<String> fontSizeComboBox;
    private File file;

    private static final String MAIN_TITLE = "W&R";
    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int DEFAULT_ICON_SIZE = 50;
    private static final int DEFAULT_BUTTON_SIZE = DEFAULT_ICON_SIZE / 2;
    private static final int DEFAULT_TIMEOUT = 100;
    private static final int DEER_WALK_MARK = 2;
    private static final int DEER_RUN_MARK = 10;
    private static final List<String> FONT_LIST = Arrays.asList("Arial", "Calibri", "Cambria", "Courier New", "Comic Sans MS", "Dialog", "Georgia", "Helevetica", "Lucida Sans", "Monospaced", "MS Sans Serif", "Tahoma", "Times New Roman", "Verdana");
    private static final String [] FONT_SIZES  = {"Font Size", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30", "40", "48", "56", "64", "72", "80", "88", "96"};

    private int types = 0;
    private int lastTickTypes = 0;
    private int ticks = 0;


    private StyledDocument getNewDocument() {
        return new DefaultStyledDocument();
    }


    private StyledDocument getEditorDocument() {
        return (DefaultStyledDocument) editor.getDocument();
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


    private URL loadResource(String path) {
        return this.getClass().getResource("/resources/" + path);
    }


    private ImageIcon rescaleIcon(ImageIcon original, int size) {
        return new ImageIcon(
                original.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)
        );
    }


    public MyEditor() {
        super(MAIN_TITLE);

        UIManager.put("TextPane.font", new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));
        deerWidget = new DeerWidget(DEFAULT_ICON_SIZE);

        var typesCounter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (types + lastTickTypes >= DEER_RUN_MARK)
                    deerWidget.setState(DeerState.Run);
                else if (types + lastTickTypes >= DEER_WALK_MARK)
                    deerWidget.setState(DeerState.Walk);
                else
                    deerWidget.setState(DeerState.Stand);

                deerWidget.nextFrame();

                if (ticks % (1000 / DEFAULT_TIMEOUT) == 0) {
                    lastTickTypes = types;
                    types = 0;
                }

                ++ticks;
            }
        };
        var timer = new Timer(DEFAULT_TIMEOUT, typesCounter);
        timer.start();
    }


    public void run() {
        editor = new JTextPane();
        var editorScrollPane = new JScrollPane(editor);

        var editButtonActionListener = new EditButtonActionListener();

        editor.setDocument(new DefaultStyledDocument());
        editor.addKeyListener(this);
        editor.setStyledDocument(getNewDocument());

        var colorButton = new JButton();
        colorButton.addActionListener(new ColorActionListener());
        colorButton.setIcon(rescaleIcon(new ImageIcon(loadResource("images/icon_paint.png")), DEFAULT_ICON_SIZE / 3));

        var boldButton = new JButton(new StyledEditorKit.BoldAction());
        boldButton.setHideActionText(true);
        boldButton.setIcon(rescaleIcon(new ImageIcon(loadResource("images/icon_bold.png")), DEFAULT_ICON_SIZE / 3));
        boldButton.addActionListener(editButtonActionListener);
        var italicButton = new JButton(new StyledEditorKit.ItalicAction());
        italicButton.setHideActionText(true);
        italicButton.setIcon(rescaleIcon(new ImageIcon(loadResource("images/icon_italic.png")), DEFAULT_ICON_SIZE / 3));
        italicButton.addActionListener(editButtonActionListener);
        var underlineButton = new JButton(new StyledEditorKit.UnderlineAction());
        underlineButton.setHideActionText(true);
        underlineButton.setIcon(rescaleIcon(new ImageIcon(loadResource("images/icon_underline.png")), DEFAULT_ICON_SIZE / 3));
        underlineButton.addActionListener(editButtonActionListener);

        Vector<String> editorFonts = getEditorFonts();
        editorFonts.add(0, "Font Family");
        fontFamilyComboBox = new JComboBox<>(editorFonts);
        fontFamilyComboBox.setEditable(false);
        fontFamilyComboBox.addItemListener(new FontFamilyItemListener());
        fontFamilyComboBox.setMaximumSize(new Dimension(DEFAULT_ICON_SIZE * 2, DEFAULT_BUTTON_SIZE));

        fontSizeComboBox = new JComboBox<>(FONT_SIZES);
        fontSizeComboBox.setEditable(false);
        fontSizeComboBox.addItemListener(new FontSizeItemListener());
        fontSizeComboBox.setMaximumSize(new Dimension(DEFAULT_ICON_SIZE * 2, DEFAULT_BUTTON_SIZE));

        var toolBar = new JToolBar("Tools");
        toolBar.setSize(1000, DEFAULT_ICON_SIZE);
        toolBar.add(deerWidget);
        toolBar.add(colorButton);
        toolBar.add(boldButton);
        toolBar.add(italicButton);
        toolBar.add(underlineButton);
        toolBar.add(fontFamilyComboBox);
        toolBar.add(fontSizeComboBox);
        toolBar.setFloatable(false);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newItem	= new JMenuItem("New");
        newItem.setMnemonic(KeyEvent.VK_N);
        newItem.addActionListener(new NewFileListener());
        JMenuItem openItem	= new JMenuItem("Open...");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.addActionListener(new OpenFileListener());
        JMenuItem saveItem	= new JMenuItem("Save (...)");
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.addActionListener(new SaveFileListener());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

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
        ++lastTickTypes;
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


    /**
     * Смена цвета текста
     */
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


    /**
     * Смена шрифта текста
     */
    private class FontFamilyItemListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if ((e.getStateChange() != ItemEvent.SELECTED) || (fontFamilyComboBox.getSelectedIndex() == 0)) {
                return;
            }

            String fontFamily = (String)e.getItem();
            fontFamilyComboBox.setAction(new StyledEditorKit.FontFamilyAction(fontFamily, fontFamily));
            fontFamilyComboBox.setSelectedIndex(0);
            editor.requestFocusInWindow();
        }
    }


    private class FontSizeItemListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if ((e.getStateChange() != ItemEvent.SELECTED) || (fontSizeComboBox.getSelectedIndex() == 0)) {
                return;
            }

            String fontSizeStr = (String)e.getItem();

            int newFontSize;
            try {
                newFontSize = Integer.parseInt(fontSizeStr);
            }
            catch (NumberFormatException ex) {
                return;
            }

            fontSizeComboBox.setAction(new StyledEditorKit.FontSizeAction(fontSizeStr, newFontSize));
            fontSizeComboBox.setSelectedIndex(0);
            editor.requestFocusInWindow();
        }
    }


    private void setFrameTitle(String title) {
        setTitle(MAIN_TITLE + " " + title);
    }


    private class NewFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            initEditorAttributes();
            editor.setDocument(getNewDocument());
            file = null;
            setFrameTitle("New file");
        }

        private void initEditorAttributes() {
            AttributeSet attrs1 = editor.getCharacterAttributes();
            var attrs2 = new SimpleAttributeSet(attrs1);
            attrs2.removeAttributes(attrs1);
            editor.setCharacterAttributes(attrs2, true);
        }
    }

    private class OpenFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            file = chooseFile();

            if (file == null) {
                return;
            }

            readFile(file);
            setFrameTitle(file.getName());
        }

        private File chooseFile() {
            var chooser = new JFileChooser();

            if (chooser.showOpenDialog(MyEditor.this) == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile();
            }
            else {
                return null;
            }
        }

        private void readFile(File file) {
            StyledDocument doc;

            try (InputStream fis = new FileInputStream(file);
                 var ois = new ObjectInputStream(fis)) {

                doc = (DefaultStyledDocument) ois.readObject();
            }
            catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(MyEditor.this, "Input file was not found!");
                return;
            }
            catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }

            editor.setDocument(doc);
        }
    }

    private class SaveFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (file == null) {
                file = chooseFile();

                if (file == null)
                    return;
            }

            var doc = (DefaultStyledDocument) getEditorDocument();

            try (OutputStream fos = new FileOutputStream(file);
                 var oos = new ObjectOutputStream(fos)) {

                oos.writeObject(doc);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            setFrameTitle(file.getName());
        }

        private File chooseFile() {
            var chooser = new JFileChooser();

            if (chooser.showSaveDialog(MyEditor.this) == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile();
            }
            else {
                return null;
            }
        }
    }
}