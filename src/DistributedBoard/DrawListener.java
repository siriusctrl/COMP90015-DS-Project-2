package DistributedBoard;

import Tools.*;
import Tools.Rectangle;
import static Utils.Logger.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawListener extends MouseAdapter implements ActionListener {
    private String toolSelected = "";
    private WhiteBoard whiteBoard;

    private Point starting;
    private Point ending;

    private Image buff;

    public DrawListener(WhiteBoard board) {
        this.whiteBoard = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("")){
            log("get \"\" action");
        } else {
            JButton button = (JButton) e.getSource();
            toolSelected = button.getActionCommand();
        }
    }

    public void mousePressed(MouseEvent e) {
        log("Mouse Pressed");
        starting = new Point(e.getX(), e.getY());
        buff = whiteBoard.getDrawingBoardPanel().createImage(
                whiteBoard.getDrawingBoardPanel().getWidth(),
                whiteBoard.getDrawingBoardPanel().getHeight()
        );
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("==== Mouse Released ====");
        ending = new Point(e.getX(), e.getY());
        Drawable current = null;
        switch (toolSelected) {
            case "Line" -> {
                log("Drawing Line");
                current = new Line(starting, ending);
            }

            case "Circle" -> {
                log("Drawing Circle");
                current = new Circle(starting, ending);
            }

            case "Rectangle" -> {
                log("Drawing Rectangle");
                current = new Rectangle(starting, ending);
            }

            case "Text" -> {
                log("Drawing Text");
                String msg = JOptionPane.showInputDialog(whiteBoard.getFrame(), "Text");

                if (msg != null && !msg.equals("")) {
                    current = new Text(starting, msg);
                }

                logError("No text input found");
            }

            default -> System.err.println("Unknown tool selected: " + toolSelected);
        }

        // remove the preview
        whiteBoard.getDrawingBoardPanel().clearBuffer();

        if (current != null) {
            whiteBoard.addDrawable(current);
        }
    }


    public void mouseDragged(MouseEvent e) {
        ending = new Point(e.getX(), e.getY());
        Drawable dragBuffer = null;

        switch (toolSelected) {
            case "Line" -> {
                dragBuffer = new Line(starting, ending);
            }

            case "Circle" -> {
                dragBuffer = new Circle(starting, ending);
            }

            case "Rectangle" -> {
                dragBuffer = new Rectangle(starting, ending);
            }

            default -> dragBuffer = null;
        }

        // only display the preview to the current user
        whiteBoard.getDrawingBoardPanel().setBuffer(dragBuffer);
        whiteBoard.getDrawingBoardPanel().repaint();
    }
}
