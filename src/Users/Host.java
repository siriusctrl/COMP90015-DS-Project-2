package Users;

import Board.BoardView;
import RMI.CreateRegistry;
import RMI.RemoteHello;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import RMI.RemoteRequest;
import Utils.UserType;
import com.beust.jcommander.Parameter;

public class Host {

    @Parameter(names={"-p", "--port"}, description = "server listening port")
    private int port = 3456;
    @Parameter(names = {"--id", "-i"}, description = "The server ID")
    private String uid = "Users";
    @Parameter(names = {"-h", "--help"}, help = true)
    private boolean help = false;

    private Registry serverRegistry;
    private ParticipantsManager participantsManager;

    public Host() {
    }


    public void setup() {
        try {
            serverRegistry = CreateRegistry.setRegistry(port);
        } catch (RemoteException e) {
            System.err.println("Error when creating Registry at Host: " + e.getMessage());
            System.exit(1);
        }

        participantsManager = new ParticipantsManager(UserType.HOST, uid);

        try {
            // bind functions
            serverRegistry.bind("hello", new RemoteHello());
            serverRegistry.bind("request", new RemoteRequest(participantsManager));
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("bind function error at Host: " + e.getMessage());
            System.exit(1);
        }

        Thread guiThread = new Thread(() -> {
            BoardView boardView = new BoardView(participantsManager);
            boardView.getFrame().setVisible(true);
        });

        guiThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(participantsManager::clearAll));
    }

    public boolean isHelp() {
        return help;
    }
}
