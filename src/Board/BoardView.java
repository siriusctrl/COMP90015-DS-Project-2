package Board;

import Users.ParticipantsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class BoardView {
    private JFrame frame;

    public static final String TITLE = "Distributed Board";
    public static final String[] TOOLS = {"Line", "Circle", "Rectangle", "Text", "Eraser"};

    private ParticipantsManager participantsManager;

    public BoardView() {
        initBoard();
    }

    public BoardView(ParticipantsManager participantsManager) {
        this.participantsManager = participantsManager;
        init();
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

        frame.setVisible(false);
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
        sub.setPreferredSize(new Dimension(300,200));
        sub.add(tempList);
        frame.getContentPane().add(sub, BorderLayout.EAST);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("A Menu");
        menu.setMnemonic('A');
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Pen", 'A');
        menuItem.addActionListener(e -> {
            System.out.println("cao");
        });
        menu.add(menuItem);


        frame.setJMenuBar(menuBar);


        // don't pop this window until needed
        frame.setVisible(false);
    }

    public JFrame getFrame() {
        return frame;
    }
}
