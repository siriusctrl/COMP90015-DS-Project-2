package Tools;

import java.awt.*;

public class Rectangle extends Drawable{

    public Rectangle(Point start, Point end) {
        super(start, end);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(COLOR);
        g2d.setStroke(new BasicStroke(STROKE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawRect(Math.min(start.x, end.x), Math.min(start.y, end.y),
                Math.abs(start.x - end.x), Math.abs(start.y - end.y));
    }
}
