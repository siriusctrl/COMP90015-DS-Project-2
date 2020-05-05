package paintboard;

import java.awt.*;

public class Circle extends Paintable{

    @Override
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawOval(min(x1, x2), min(y1, y2),
                    max(abs(x1-x2), abs(y1-y2)),
                    max(abs(x1-x2), abs(y1-y2))
                );
    }
}
