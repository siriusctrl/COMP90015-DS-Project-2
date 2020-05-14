package Tools;

import java.io.Serializable;
import java.awt.*;


public abstract class Drawable implements Serializable {
    // each drawing tools should extend this class
    public String msg = "This is a drawable";
    protected Point start;
    protected Point end;

    protected final Color COLOR = Color.black;
    protected final int STROKE = 2;

    public Drawable(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public abstract void draw(Graphics2D g2d);
}
