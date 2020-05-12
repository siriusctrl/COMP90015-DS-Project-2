package Users;

import RMI.CreateRegistry;
import RMI.RemoteHello;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import RMI.RemoteRequest;
import com.beust.jcommander.Parameter;

public class Server {

    @Parameter(names={"-p", "--port"}, description = "server listening port")
    private int port = 3456;
    @Parameter(names = {"--id", "-i"}, description = "The server ID")
    private String uid = "Users";
    @Parameter(names = {"-h", "--help"}, help = true)
    private boolean help = false;

    private Registry serverRegistry;
    private ParticipantsManager participantsManager;

    public Server() {
    }


    public void setup() {
        try {
            serverRegistry = CreateRegistry.setRegistry(port);
        } catch (RemoteException e) {
            System.err.println("Error when creating Registry at Server: " + e.getMessage());
            System.exit(1);
        }

        participantsManager = new ParticipantsManager(this);

        try {
            // bind functions
            serverRegistry.bind("hello", new RemoteHello());
            serverRegistry.bind("join", new RemoteRequest(participantsManager));
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("bind function error at Server: " + e.getMessage());
            System.exit(1);
        }
    }

    public void reloadWaitingList() {
        // todo : invoke GUI method or later call GUI directly in manager
        System.out.println("Reloading waiting list");

        // todo : simply allow everything, remove these once GUI are set
        for (String uid: participantsManager.getAllWaiting()) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Allowing user: " + uid);
                participantsManager.allowJoin(uid);
                participantsManager.updateBoard(uid);
//                participantsManager.rejectJoin(uid);
            } catch (RemoteException e) {
                System.err.println("Cannot join user: " + uid + ", due to: " + e.getMessage());
            }
        }
    }

    public String getUid() {
        return uid;
    }

    public boolean isHelp() {
        return help;
    }
}
