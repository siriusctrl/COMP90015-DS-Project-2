package Board;

import Tools.Drawable;
import Users.ParticipantsManager;
import static Utils.Logger.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Vector;

public class BoardView {
    private JFrame frame;

    public static final String TITLE = "Distributed Board";
    public static final String[] TOOLS = {"Pen", "Line", "Circle", "Rectangle", "Text", "None"};

    private ParticipantsManager participantsManager;
    private DrawBoard drawArea;
    private DrawBoardManager drawBoardManager;
    private MouseHandler mouseHandler;

    private String savePath;

    public BoardView(ParticipantsManager participantsManager) {
        this.participantsManager = participantsManager;
        participantsManager.setBoardView(this);
        init();
    }

    public void addDrawable(Drawable d) {
        drawBoardManager.addDrawable(d);
    }

    public void init() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1000, 700);
        frame.setTitle(TITLE + ": " + participantsManager.getCurrentUid());
        frame.setResizable(true);

        frame.getContentPane().add(
                new ParticipantListPanel(this.getFrame(), participantsManager),
                BorderLayout.EAST
        );

        addMenu();

        // Set Board
        mouseHandler = new MouseHandler(this);
        addDrawingArea();

        // Set Board ends here

        // Set Tools
        JPanel drawToolPanel = new JPanel();

        drawToolPanel.setPreferredSize(new Dimension(110, 0));
        frame.getContentPane().add(drawToolPanel, BorderLayout.WEST);
        drawToolPanel.setLayout(new BorderLayout(0 ,0));
        JPanel toolPanel = new JPanel();

        toolPanel.setBorder(new TitledBorder(null, "Tool Bar",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        toolPanel.setLayout(new GridLayout(0, 1, 0, 0));
        toolPanel.setPreferredSize(new Dimension(0, 300));
        drawToolPanel.add(toolPanel, BorderLayout.NORTH);

        JTextPane toolNow = new JTextPane();
        toolNow.setText("None");
        toolNow.setOpaque(false);
        toolNow.setEditable(false);
        JPanel displayTool = new JPanel();
        displayTool.add(toolNow);
        drawToolPanel.add(displayTool, BorderLayout.CENTER);

        for(String tool:TOOLS) {
            JButton bt = new JButton(tool);
            bt.setCursor(new Cursor(Cursor.HAND_CURSOR));
            toolPanel.add(bt);
            bt.addActionListener((e) -> {
                toolNow.setText(e.getActionCommand());
                mouseHandler.setToolSelected(e.getActionCommand());
            });
        }

        // Tools Setting end here

        frame.setVisible(false);
    }

    public void addDrawingArea() {
        drawBoardManager = new DrawBoardManager(participantsManager);
        drawArea = new DrawBoard(drawBoardManager);
        drawBoardManager.setDrawBoard(drawArea);
        drawArea.setBorder(new TitledBorder(null, "Drawing Area",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        drawArea.addMouseListener(mouseHandler);
        drawArea.addMouseMotionListener(mouseHandler);

        drawBoardManager.setDrawBoard(drawArea);

        drawBoardManager.setHistory(new Vector<>());
        drawArea.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        frame.getContentPane().add(drawArea, BorderLayout.CENTER);
    }

    public void addMenu() {

        JMenuBar menuBar = new JMenuBar();

        // file menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.setMnemonic('F');
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");

        JMenuItem newItem = new JMenuItem("New", 'F');
        JMenuItem openItem = new JMenuItem("Open", 'F');
        JMenuItem saveItem = new JMenuItem("Save", 'F');
        JMenuItem saveAsItem = new JMenuItem("Save As", 'F');
        JMenuItem closeItem = new JMenuItem("Close", 'F');

        newItem.addActionListener(e -> {
            if (participantsManager.isHost()) {
                drawBoardManager.clearHistory();
                savePath = null;
                participantsManager.notifyOthers("Host open a new board!");
            } else {
                JOptionPane.showMessageDialog(this.frame, "You are not the host!");
            }
        });

        openItem.addActionListener(e -> {
            if(participantsManager.isHost()) {
                openBoard();
                participantsManager.notifyOthers("Host open a saved board!");
            } else {
                JOptionPane.showMessageDialog(this.frame, "You are not the host!");
            }
        });

        closeItem.addActionListener(e -> {
            System.exit(0);
        });

        saveItem.addActionListener(e -> {
            if (participantsManager.isHost()) {
                if(savePath != null) {
                    saveTo(savePath);
                } else {
                    saveAs();
                }
            } else {
                JOptionPane.showMessageDialog(this.frame, "You are not the host!");
            }
        });

        saveAsItem.addActionListener(e -> {
            if(participantsManager.isHost()) {
                saveAs();
            } else {
                JOptionPane.showMessageDialog(this.frame, "You are not the host!");
            }
        });

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(closeItem);

        frame.setJMenuBar(menuBar);
    }

    public void openBoard() {
        JFileChooser chooser = new JFileChooser(".");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "Board files", "draw"));

        int value = chooser.showOpenDialog(this.frame);

        if (value == JFileChooser.APPROVE_OPTION) {
            String openPath = chooser.getSelectedFile().getPath();
            try {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(openPath));
                setHistory((Vector<Drawable>) input.readObject());
                savePath = openPath;
            } catch (IOException | ClassNotFoundException e) {
                logError("Cannot open target file due to: " + e.getMessage());
            }

        }
    }

    public void saveTo(String path) {
        Vector<Drawable> history = getDrawBoardManager().getHistory();
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(history);
            out.close();
            System.out.println(path);
        } catch (IOException ioe) {
            logError(ioe);
        }
    }

    public void saveAs() {
        JFileChooser chooser = new JFileChooser(".");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "Board files", "draw"));

        int value = chooser.showSaveDialog(this.frame);

        if (value == JFileChooser.APPROVE_OPTION) {
            String format = ((FileNameExtensionFilter) chooser.getFileFilter()).getExtensions()[0];
            String savePath = chooser.getSelectedFile().getPath();
            if (format.equals("draw")) {
                Vector<Drawable> history = getDrawBoardManager().getHistory();

                try {
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath));
                    out.writeObject(history);
                    out.close();
                    this.savePath = savePath;
                    System.out.println(savePath);
                } catch (IOException ioe) {
                    logError(ioe);
                }
            }
            // todo : add support for other file type
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public DrawBoard getDrawArea() {
        return drawArea;
    }

    public void setHistory(Vector<Drawable> history) {
        drawBoardManager.setHistory(history);
    }

    public DrawBoardManager getDrawBoardManager() {
        return drawBoardManager;
    }
}
