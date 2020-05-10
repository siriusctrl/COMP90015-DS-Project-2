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
        Feedback feedback = client.join();

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
}
