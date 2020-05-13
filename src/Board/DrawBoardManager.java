package Board;

import Tools.Drawable;
import Users.ParticipantsManager;

import java.util.Vector;

public class DrawBoardManager {
    private Vector<Drawable> history;
    private DrawBoard drawBoard;
    private ParticipantsManager participantsManager;

    public DrawBoardManager(DrawBoard drawBoard, ParticipantsManager participantsManager) {
        this.drawBoard = drawBoard;
        this.participantsManager = participantsManager;

    }

    // FIXME : this is only for testing in boardview initBoard
    public DrawBoardManager() {
    }

    public void setDrawBoard(DrawBoard drawBoard) {
        this.drawBoard = drawBoard;
    }

    public Vector<Drawable> getHistory() {
        return history;
    }

    public void setHistory(Vector<Drawable> history) {
        this.history = history;
        // todo : look what is menu bar
        // todo : implement update remote board
        //participantsManager.updateOtherBoard(history);
        drawBoard.repaint();
    }
}
