package Board;

import Tools.*;
import Tools.Rectangle;

import static Utils.Logger.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter implements ActionListener {
    private Point starting, ending;
    private BoardView boardView;

    private String toolSelected;

    private Image imgBuffer;

    public MouseHandler(BoardView boardView) {
        this.boardView = boardView;
        toolSelected = "None";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!e.getActionCommand().equals("")){
            toolSelected = ((JButton) e.getSource()).getActionCommand();
        }
    }

    public void mousePressed(MouseEvent e) {
        starting = new Point(e.getX(), e.getY());
        imgBuffer = boardView.getDrawArea().createImage(boardView.getDrawArea().getWidth(),
                boardView.getDrawArea().getHeight());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ending = new Point(e.getX(), e.getY());

        Drawable needDraw = null;

        switch (toolSelected) {
            case ("Line") -> needDraw = new Line(starting, ending);
            case ("Circle") -> needDraw = new Circle(starting, ending);
            case ("Rectangle") -> needDraw = new Rectangle(starting, ending);
            case ("Text") -> {
                String text = JOptionPane.showInputDialog(boardView.getFrame(), "Text: ");
                if (text != null && text != "") {
                    needDraw = new Text(ending, text);
                }
            }
            default -> logError("Unknown Tool: " + toolSelected);
        }

        boardView.getDrawArea().clearBuffer();
        if (needDraw != null) {
            boardView.addDrawable(needDraw);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ending = new Point(e.getX(), e.getY());

        Drawable preview = null;

        switch (toolSelected) {
            case ("Line") -> preview = new Line(starting, ending);
            case ("Circle") -> preview = new Circle(starting, ending);
            case ("Rectangle") -> preview = new Rectangle(starting, ending);
        }

        boardView.getDrawArea().setBuffer(preview);
    }

    public void setToolSelected(String toolSelected) {
        this.toolSelected = toolSelected;
    }
}
