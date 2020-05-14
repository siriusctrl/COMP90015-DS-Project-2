package Tools;

import java.awt.*;
import java.util.Vector;

public class Pen extends Drawable {
    private Vector<Point> trace;

    public Pen() {
        super(null, null);
        trace = new Vector<>();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(COLOR);
        g2d.setStroke(new BasicStroke(STROKE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

        for (int i = 0; i < trace.size() - 1; i++) {
            g2d.drawLine(trace.get(i).x, trace.get(i).y, trace.get(i+1).x, trace.get(i+1).y);
        }
    }

    public void addTrace(Point point) {
        trace.add(point);
    }
}
