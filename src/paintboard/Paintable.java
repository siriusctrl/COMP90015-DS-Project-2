package paintboard;

import java.awt.*;
import java.io.Serializable;

public abstract class Paintable implements Serializable {
    public int x1, y1, x2, y2;
    public int R, G, B;
    public float stroke;
    public int type;
    String s1;
    String s2;

    abstract void draw(Graphics2D g2d);

    public static int min(int a, int b) {
        return a>b?b:a;
    }

    public static int max(int a, int b) {
        return a>b?a:b;
    }

    public static int abs(int a) {
        return a>=0?a:(-a);
    }
}
