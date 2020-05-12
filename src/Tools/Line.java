package Tools;

import java.awt.*;

public class Line extends Drawable{
    public Line(Point start, Point end) {
        super(start, end);
    }

    @Override
    public void draw(Graphics2D g2d) {
        System.out.println("draw");
    }
}
