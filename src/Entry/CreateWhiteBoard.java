package Entry;

import Users.Host;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class CreateWhiteBoard {
    public static void main(String[] args) {
        System.out.println("Try to create white board");
        Host host = new Host();
        try {
            JCommander commander = JCommander.newBuilder().addObject(host).build();
            commander.parse(args);

            // print help message and then exit
            if (host.isHelp()) {
                commander.usage();
                System.exit(0);
            }
        } catch (ParameterException e) {
            System.err.println(e.getMessage() + "\n Please use -h or --help for usage");
            System.exit(1);
        }

        host.setup();
        System.out.println("Board Set");
    }
}
