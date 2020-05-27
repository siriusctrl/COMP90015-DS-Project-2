package Entry;

import Users.Participant;
import Utils.Feedback;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class JoinWhiteBoard {
    public static void main(String[] args) {
        Participant participant = new Participant();

        // parse the arguments
        try {
            JCommander commander = JCommander.newBuilder().addObject(participant).build();
            commander.parse(args);

            // print help message and then exit
            if (participant.isHelp()) {
                commander.usage();
                System.exit(0);
            }
        } catch (ParameterException e) {
            System.err.println(e.getMessage() + "\n Please use -h or --help for usage");
            System.exit(1);
        }

        System.out.println("==== joining the server ====");

        // try to join the server's board
        handleJoin(participant.join());

        // add here to remove self from both waiting list and
        Runtime.getRuntime().addShutdownHook(new Thread(participant::removeSelf));
    }

    public static void handleJoin(Feedback feedback) {
        switch (feedback.getState()) {
            case ERROR -> {
                System.err.println(feedback.getMsg());
                System.exit(1);
            }
            case FAILED -> {
                System.out.println(feedback.getMsg());
                System.exit(1);
            }
            case SUCCEED -> {
                System.out.println(feedback.getMsg());
            }
        }
    }
}
