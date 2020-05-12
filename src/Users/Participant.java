package Users;

import Board.BoardView;
import Feedback.*;
import RMI.*;
import Utils.UserType;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Participant {

    @Parameter(names = { "-u ", "--userid" }, description = "unique userId")
    private static String userId = "bob";
    @Parameter(names = {"-a", "--address"}, description =  "server Ip address")
    private static String serverIp = "localhost";
    @Parameter(names = {"-p", "--port"}, description = "serverPort")
    private static int serverPort = 3456;
    @Parameter(names = {"-h", "--help"}, help = true)
    private static boolean help = false;

    private int selfPort;
    private Registry selfRegistry;
    private RemoteBoard board;

    private Registry serverRegistry;

    private IRemoteRequest remoteRequest;

    private ParticipantsManager participantsManager;

    public Participant() {
        // setup own RMI for receiving message from the host
        try {
            this.selfPort = CreateRegistry.getRegistryPort();
            this.selfRegistry = LocateRegistry.createRegistry(selfPort);
            board = new RemoteBoard(this);
            selfRegistry.bind("board", board);
        } catch (IOException | AlreadyBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //setup a participants manager in user mode
        participantsManager = new ParticipantsManager(UserType.PARTICIPANT, userId);
    }

    public Feedback join() {

        getServerRegistry();

        try {
            remoteRequest = (IRemoteRequest) serverRegistry.lookup("request");
            Feedback feedback = remoteRequest.joinRequest(userId, serverIp, selfPort);
            Runtime.getRuntime().addShutdownHook(new Thread(this::removeSelf));
            return feedback;
        } catch (RemoteException | NotBoundException e) {
            return new Feedback(FeedbackState.ERROR, "Joining Error: " + e.getMessage());
        }
    }

    public void getServerRegistry() {
        try {
            if (serverRegistry == null) {
                this.serverRegistry = LocateRegistry.getRegistry(serverIp, serverPort);
            }
        } catch (RemoteException e) {
            System.err.println("Cannot get remote Registry, please make sure your IP and port are correct");
            System.exit(1);
        }
    }

    public void invokeBoard(String hostId) {
        System.out.println("Invoke your board view");
        participantsManager.setHostId(hostId);
        participantsManager.setHostReq(remoteRequest);

        Thread board = new Thread(() -> {
            BoardView boardView = new BoardView(participantsManager);
            boardView.getFrame().setVisible(true);
        });

        board.start();
    }

    public void exit() {
        try {
            selfRegistry.unbind("board");
            UnicastRemoteObject.unexportObject(board, true);
            System.out.println("Exiting");
        } catch (NotBoundException | RemoteException e) {
            System.err.println(e.getMessage());
        }

        System.exit(0);
    }

    public void removeSelf() {
        System.out.println("remove self from host list");
        try {
            remoteRequest.removeUserRequest(userId);
        } catch (RemoteException e) {
            System.err.println("Cannot remove my self, exit!");
        }
    }

    public ParticipantsManager getParticipantsManager() {
        return participantsManager;
    }

    public boolean isHelp() {
        return help;
    }
}
