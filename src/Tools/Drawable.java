package Tools;

import java.io.Serializable;
import java.awt.*;


public abstract class Drawable implements Serializable {
    // each drawing tools should extend this class
    protected String msg = "This is a drawable";
    protected Point start;
    protected Point end;

    protected final Color COLOR = Color.black;
    protected final int STROKE = 3;

    public Drawable(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public abstract void draw(Graphics2D g2d);
}
