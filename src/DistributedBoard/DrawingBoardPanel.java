package DistributedBoard;

import Tools.Drawable;

import javax.swing.*;
import java.awt.*;

public class DrawingBoardPanel extends JPanel {
    private PaintManager paintManager;
    private Drawable buffer;
    private Image offScreen;

    public DrawingBoardPanel(PaintManager paintManager) {
        super();
        this.paintManager = paintManager;
        this.setBackground(Color.white);
    }

    /**
     * Repaint will invoke this function directly to re-draw the board
     */
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // re-draw the whole painting
        for(Drawable shape:paintManager.getBoardHistory()) {
            shape.draw(g2d);
        }

        if (buffer != null) {
            buffer.draw(g2d);
        }
    }

    public void update(Graphics g) {
        if (offScreen == null) {
            offScreen = this.createImage(getWidth(), getHeight());
        }

        paint(offScreen.getGraphics());
        g.drawImage(offScreen, 0, 0, null);
    }

    public void clearBuffer() {
        buffer = null;
    }

    public void setBuffer(Drawable shape) {
        this.buffer = shape;
    }

}
