package Board;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class BoardView {
    private JFrame frame;

    public static final String TITLE = "Distributed Board";

    public static final String[] TOOLS = {"Line", "Circle", "Rectangle", "Text", "Eraser"};

    public BoardView() {
        initBoard();
    }

    private void initBoard() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // When close the window, it should remove its information in the system.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // todo : implementing remove logic here for both server and participants
            }
        });

        frame.setSize(1000, 700);
        frame.setTitle(TITLE);
        frame.setResizable(true);

//        JButton tempButton = new JButton("What?");
//
//        frame.add(tempButton);
//
//        tempButton.addActionListener((e) -> {
//            int result = JOptionPane.showConfirmDialog(frame, "Allow him in?",
//                    "Guest Management", JOptionPane.YES_NO_CANCEL_OPTION);
//
//            switch (result) {
//                case (0) -> System.out.println("yes");
//                case (1) -> System.out.println("no");
//                case (2) -> System.out.println("cancel");
//            }
//        });
//
//        JList<String> tempList = new JList<>();
//        Vector<String> testData = new Vector<>();
//        testData.add("[guest] cao");
//        testData.add("[visitor] Dajiba");
//        tempList.setListData(testData);
//        tempList.addListSelectionListener((e) -> {
//            if(!tempList.getValueIsAdjusting()) {
//                String selected = tempList.getSelectedValue();
//
//                if (selected != null) {
//                    String[] temp = selected.split("]");
//
//                    if (temp[1] != null) {
//                        int result = JOptionPane.showConfirmDialog(frame, "Allow |" + temp[1].strip() + "| in?",
//                                "Guest Management", JOptionPane.YES_NO_CANCEL_OPTION);
//
//                        switch (result) {
//                            case (0) -> {
//                                System.out.println("yes");
//                                testData.remove(1);
//                                testData.add("[guest] Dajiba");
//                                tempList.setListData(testData);
//                            }
//                            case (1) -> System.out.println("no");
//                            case (2) -> System.out.println("cancel");
//                        }
//                    }
//                }
//            }
//        });
//        frame.add(tempList);



        // todo : add buttons

        // don't pop this window until needed
        frame.setVisible(false);
    }

    public JFrame getFrame() {
        return frame;
    }
}
