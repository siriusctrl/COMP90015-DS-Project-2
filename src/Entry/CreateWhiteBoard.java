package Entry;

import Server.Server;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class CreateWhiteBoard {
    public static void main(String[] args) {
        System.out.println("Try to create white board");
        // todo : set entry for server here

        Server server = new Server();

        try {
            JCommander commander = JCommander.newBuilder().addObject(server).build();
            commander.parse(args);

            // print help message and then exit
            if (server.isHelp()) {
                commander.usage();
                System.exit(0);
            }
        } catch (ParameterException e) {
            System.err.println(e.getMessage() + "\n Please use -h or --help for usage");
            System.exit(1);
        }

        server.setup();

    }
}
