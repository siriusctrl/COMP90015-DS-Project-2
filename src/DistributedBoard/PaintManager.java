package DistributedBoard;

import Server.ClientsManager;
import Tools.Drawable;
import Utils.Logger.*;

import static Utils.Logger.*;

import java.util.Vector;

public class PaintManager {
    // maintain a vector of history for board updating, using vector to prevent unexpected asynchronous
    // access many arise later for eraser tool.
    private Vector<Drawable> boardHistory;
    private DrawingBoardPanel drawingArea;
//    private ToolsMenu toolsMenu;
    private ClientsManager clientsManager;

    public final int MODE;

    public static final int MANAGER_MODE = 0;
    public static final int PARTICIPANT_MODE = 1;

    public PaintManager(int MODE, ClientsManager clientsManager) {
        //todo : different might have different activities and GUI
        this.MODE = MODE;
        this.clientsManager = clientsManager;
        boardHistory = new Vector<>();
    }

    /**
     * add one drawable to the Vector
     * @param drawable Defined in Tool packages that can be draw on the drawing board
     */
    public synchronized void addDrawable(Drawable drawable) {
        if (MODE == MANAGER_MODE) {
            boardHistory.add(drawable);
            // todo : finish this
            // broadcast to participants
        } else if (MODE == PARTICIPANT_MODE) {
            // todo : finish this
            // paint on its own board and send to server
        } else {
            logError("Unknown MODE");
        }
    }


    /**
     * Clear the board, however, only manager can do this
     */
    public void clearBoard() {
        // todo : check this in WhiteBoard
        if(MODE == MANAGER_MODE) {
            boardHistory.clear();
            drawingArea.removeAll();
            drawingArea.revalidate();
            drawingArea.repaint();
            // need to clean
        }
    }


    public Vector<Drawable> getBoardHistory() {
        return boardHistory;
    }

    public DrawingBoardPanel getDrawingArea() {
        return drawingArea;
    }

    public void setDrawingArea(DrawingBoardPanel drawingArea) {
        this.drawingArea = drawingArea;
    }


}
