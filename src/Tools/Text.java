package Tools;

import java.awt.*;

public class Text extends Drawable {

    String msg = "";

    public Text(Point ending, String msg) {
        super(null, ending);
        this.msg = msg;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setFont(new Font("Fira Code", Font.PLAIN, STROKE*16));
        g2d.setColor(COLOR);
        g2d.drawString(msg, end.x, end.y);
    }
}