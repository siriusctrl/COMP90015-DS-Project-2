package Utils;

import java.io.Serializable;

public class Feedback implements Serializable {
    private FeedbackState state;
    private String msg = "";

    public Feedback(FeedbackState state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public FeedbackState getState() {
        return state;
    }
}
