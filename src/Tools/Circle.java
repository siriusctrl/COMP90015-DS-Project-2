package Tools;

import java.awt.*;

public class Circle extends Drawable {

    public Circle(Point start, Point end) {
        super(start, end);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(COLOR);
        g2d.setStroke(new BasicStroke(STROKE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

        int x1 = start.x, x2 = end.x, y1 = start.y, y2 = end.y;
        // use this to set both weight and height as circle has one uniform radius
        int w = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
        g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), w, w);
    }
}
