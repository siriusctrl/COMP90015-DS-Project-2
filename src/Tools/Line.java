package Tools;

import java.awt.*;

public class Line extends Drawable {
    public Line(Point start, Point end) {
        super(start, end);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(COLOR);
        g2d.setStroke(new BasicStroke(STROKE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(start.x, start.y, end.x, end.y);
    }
}
