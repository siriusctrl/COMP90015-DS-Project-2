import paintboard.PaintBoardFrame;
import paintboard.Paintable;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String ... args) {
        PaintBoardFrame board = new PaintBoardFrame();

        board.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(1);
                    }
                }
        );
    }
}
