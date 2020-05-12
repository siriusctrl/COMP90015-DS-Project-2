package DistributedBoard;

import Server.ClientsManager;
import Tools.Drawable;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WhiteBoard {
    private DrawingBoardPanel drawingBoardPanel;
    private PaintManager paintManager;
    private JFrame frame;
    private ClientsManager clientsManager;


    private static final String[] TOOLS = {"Line", "Circle", "Rectangle", "Text"};
    public static final String TITLE = "Distributed WhiteBoard";


    public WhiteBoard(PaintManager paintManager, ClientsManager clientsManager) {
        this.paintManager = paintManager;
        this.clientsManager = clientsManager;
        initView();
    }


    private void initView() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                if (paintManager.MODE == PaintManager.PARTICIPANT_MODE) {
                    // todo : exit the program here
                } else {
                    clientsManager.shutDown();
                }
            }
        });

        frame.setSize(1000,700);
        frame.setTitle(TITLE);
        frame.setResizable(true);

        DrawListener drawListener = new DrawListener(this);

        JPanel userPanel = new JPanel();
        userPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        userPanel.setPreferredSize(new Dimension(200, 0));
        userPanel.setBackground(Color.WHITE);
        frame.getContentPane().add(userPanel, BorderLayout.EAST);
        userPanel.setLayout(new BorderLayout(0, 0));

        JPanel userControlPanel = new JPanel();
        userControlPanel.setPreferredSize(new Dimension(0, 300));
        userPanel.add(userControlPanel, BorderLayout.NORTH);
        userControlPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblUserList = new JLabel("User List:");
        userControlPanel.add(lblUserList, BorderLayout.NORTH);


    }


    public void addDrawable(Drawable drawable) {
        paintManager.addDrawable(drawable);
    }

    public void clearBoard() {
        paintManager.clearBoard();
    }

    public void illustrateBuffer(Drawable drawable) {
        drawingBoardPanel.setBuffer(drawable);
        drawingBoardPanel.repaint();
    }

    public DrawingBoardPanel getDrawingBoardPanel() {
        return drawingBoardPanel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
