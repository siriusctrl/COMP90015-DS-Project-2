package Board;

import Tools.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class DrawBoard extends JPanel {
    private DrawBoardManager drawBoardManager;
    private Drawable buffer;
    private Image offScreenImage;

    public DrawBoard (DrawBoardManager drawBoardManager) {
        super();
        this.drawBoardManager = drawBoardManager;
        this.setBackground(Color.white);
    }


    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(getWidth(), getHeight());
        }

        Graphics gi = offScreenImage.getGraphics();
        paint(gi);
        g.drawImage(offScreenImage,0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        HashMap<RenderingHints.Key, Object> hashMap = new HashMap<>();

        hashMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        hashMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(hashMap);

        for (Drawable d: drawBoardManager.getHistory()) {
            d.draw(g2d);
        }

        if (buffer != null) {
            buffer.draw(g2d);
        }
    }

    public void setBuffer(Drawable buffer) {
        this.buffer = buffer;
        repaint();
    }

    public void clearBuffer() {
        this.buffer = null;
    }


}
