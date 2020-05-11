package DistributedBoard;

import Tools.Drawable;

import javax.swing.*;

public class WhiteBoard {
    private DrawingBoardPanel drawingBoardPanel;
    private PaintManager paintManager;
    private JFrame frame;


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
