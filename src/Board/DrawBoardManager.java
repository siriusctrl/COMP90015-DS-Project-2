package Board;

import Tools.Drawable;
import Users.ParticipantsManager;

import java.util.Vector;

public class DrawBoardManager {
    private Vector<Drawable> history;
    private DrawBoard drawBoard;
    private ParticipantsManager participantsManager;

    public DrawBoardManager(ParticipantsManager participantsManager) {
        this.participantsManager = participantsManager;
        history = new Vector<>();
    }

    // FIXME : this is only for testing in boardview initBoard
    public DrawBoardManager() {
    }

    public void setDrawBoard(DrawBoard drawBoard) {
        this.drawBoard = drawBoard;
    }

    public Vector<Drawable> getHistory() {
        if (participantsManager.isHost()) {
            return history;
        }

        return participantsManager.getHistory();
    }

    /**
     * This will be called when new participant join the board
     * @param history the full history so far for new participant
     *                to quickly catching up
     */
    public void setHistory(Vector<Drawable> history) {
        this.history = history;
        repaint();
    }

    public void addDrawable(Drawable drawable) {
        if (participantsManager.isHost()) {
            history.add(drawable);
        }
        participantsManager.addHistory(drawable);

        repaint();
    }

    public void repaint() {
        drawBoard.repaint();
    }

}
