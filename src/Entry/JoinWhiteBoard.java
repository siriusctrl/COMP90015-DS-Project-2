package Entry;

import Client.Client;
import Feedback.Feedback;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class JoinWhiteBoard {
    public static void main(String[] args) {
        Client client = new Client();

        // parse the arguments
        try {
            JCommander commander = JCommander.newBuilder().addObject(client).build();
            commander.parse(args);

            // print help message and then exit
            if (client.isHelp()) {
                commander.usage();
                System.exit(0);
            }
        } catch (ParameterException e) {
            System.err.println(e.getMessage() + "\n Please use -h or --help for usage");
            System.exit(1);
        }

        System.out.println("==== joining the server ====");

        // try to join the server's board
        handleJoin(client.join());

        // todo: use another thread to suspend until Allow/Reject was called to exit/setVisible
        handleAgree(client.waitUntilResponds());
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
            case SUCCEED -> System.out.println(feedback.getMsg());
        }
    }


    public static void handleAgree(Feedback feedback) {
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
                // todo : now can set the client board view visible
                System.out.println("setting the view ...");
            }
        }
    }
}
