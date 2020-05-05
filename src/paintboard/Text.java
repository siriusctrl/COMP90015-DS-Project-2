package paintboard;

import java.awt.*;

public class Text extends Paintable {

    @Override
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setFont(new Font(s2, x2 + y2, ((int) stroke) * 16));
        if (s1 != null) {
            g2d.drawString(s1, x1, y1);
        }
    }
}
