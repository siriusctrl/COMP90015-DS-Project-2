package paintboard;

import java.awt.*;

public class Text extends Paintable {
    // set the default font for the display text
    private String font = "Avenir Next";
    private String text = "";

    @Override
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setFont(new Font(font, x2 + y2, ((int) stroke) * 13));
        if (text != null) {
            g2d.drawString(text, x1, y1);
        }
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setText(String text) {
        this.text = text;
    }

}
