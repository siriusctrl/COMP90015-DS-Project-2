package Board;

import Tools.Drawable;
import Users.ParticipantsManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class BoardView {
    private JFrame frame;

    public static final String TITLE = "Distributed Board";
    public static final String[] TOOLS = {"Pen", "Line", "Circle", "Rectangle", "Text", "None"};

    private ParticipantsManager participantsManager;
    private DrawBoard drawArea;
    private DrawBoardManager drawBoardManager;
    private MouseHandler mouseHandler;

    public BoardView() {
        initBoard();
    }

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

        JTextPane toolnow = new JTextPane();
        toolnow.setText("None");
        toolnow.setOpaque(false);
        toolnow.setEditable(false);
        JPanel displayTool = new JPanel();
        displayTool.add(toolnow);
        drawToolPanel.add(displayTool, BorderLayout.CENTER);

        for(String tool:TOOLS) {
            JButton bt = new JButton(tool);
            bt.setCursor(new Cursor(Cursor.HAND_CURSOR));
            toolPanel.add(bt);
            bt.addActionListener((e) -> {
                toolnow.setText(e.getActionCommand());
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
        // todo : remove this as the host will set your board
        drawBoardManager.setHistory(new Vector<>());
        drawArea.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        frame.getContentPane().add(drawArea, BorderLayout.CENTER);
    }

    public void addMenu() {
        if (!participantsManager.isHost()) {
            return;
        }

        JMenuBar menuBar = new JMenuBar();

        // file menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.setMnemonic('F');
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");

        JMenuItem newItem = new JMenuItem("new", 'F');
        JMenuItem openItem = new JMenuItem("open", 'F');
        JMenuItem saveItem = new JMenuItem("save", 'F');
        JMenuItem saveAsItem = new JMenuItem("save As", 'F');
        JMenuItem closeItem = new JMenuItem("close", 'F');

        openItem.addActionListener(e -> {
            System.out.println("Open");
        });


        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(closeItem);

        frame.setJMenuBar(menuBar);
    }

    /**
     * deprecated and only for testing
     */
    private void initBoard() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // When close the window, it should remove its information in the system.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        frame.setSize(1000, 700);
        frame.setTitle(TITLE);
        frame.setResizable(true);

        JList<String> tempList = new JList<>();
        Vector<String> testData = new Vector<>();
        testData.add("[guest] cao");
        testData.add("[visitor] Dajiba");
        tempList.setListData(testData);
        tempList.addListSelectionListener((e) -> {
            if(!tempList.getValueIsAdjusting()) {
                String selected = tempList.getSelectedValue();

                if (selected != null) {
                    String[] temp = selected.split("]");

                    if (temp[1] != null) {
                        int result = JOptionPane.showConfirmDialog(frame, "Allow |" + temp[1].strip() + "| in?",
                                "Guest Management", JOptionPane.YES_NO_CANCEL_OPTION);

                        switch (result) {
                            case (0) -> {
                                System.out.println("yes");
                                testData.remove(1);
                                testData.add("[guest] Dajiba");
                                tempList.setListData(testData);
                            }
                            case (1) -> System.out.println("no");
                            case (2) -> System.out.println("cancel");
                        }
                    }
                }
            }
        });

        JPanel sub = new JPanel(new BorderLayout(0, 0));
        sub.setPreferredSize(new Dimension(200,200));
        sub.add(tempList);
        sub.setBorder(new TitledBorder(null, "Participants",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        frame.getContentPane().add(sub, BorderLayout.EAST);

        addMenu();

        // ANCHOR: set drawing tools

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

        JTextPane toolnow = new JTextPane();
        toolnow.setText("Pen");
        JPanel displayTool = new JPanel();
        displayTool.add(toolnow);
        drawToolPanel.add(displayTool, BorderLayout.CENTER);

        JButton bt = null;
        for(String tool:TOOLS) {
            bt = new JButton(tool);
            bt.setCursor(new Cursor(Cursor.HAND_CURSOR));
            toolPanel.add(bt);
            bt.addActionListener((e) -> {
                // set the selected tool here
                toolnow.setText(e.getActionCommand());
            });
        }

        // set board
        DrawBoardManager manager = new DrawBoardManager();
        DrawBoard drawArea = new DrawBoard(manager);

        drawArea.setBorder(new TitledBorder(null, "Drawing Area",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        manager.setDrawBoard(drawArea);
        manager.setHistory(new Vector<>());
        drawArea.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        frame.getContentPane().add(drawArea, BorderLayout.CENTER);

        // don't pop this window until needed
        frame.setVisible(false);
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
