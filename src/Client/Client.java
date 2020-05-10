package Client;

import Feedback.*;
import RMI.*;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

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

    private Registry serverRegistry;

    private boolean responds = false;
    private boolean allowJoin = false;

    public Client() {
        try {
            this.selfPort = CreateRegistry.getRegistryPort();
            this.selfRegistry = LocateRegistry.createRegistry(selfPort);
            selfRegistry.bind("board", new RemoteBoard(this));
        } catch (IOException | AlreadyBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Feedback join() {
        getServerRegistry();

        try {
            IRemoteRequest remoteJoin = (IRemoteRequest) serverRegistry.lookup("join");
            return remoteJoin.joinRequest(userId, serverIp, selfPort);
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

    public synchronized Feedback waitUntilResponds() {
        while (!responds) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                return new Feedback(FeedbackState.ERROR, "Waiting for responds suspension failed! "
                        + e.getMessage());
            }
        }

        if (allowJoin) {
            return new Feedback(FeedbackState.SUCCEED, "server allow you to join");
        }

        return new Feedback(FeedbackState.FAILED, "server reject join request");
    }

    public boolean isHelp() {
        return help;
    }

    public void setAllowJoin(boolean allowJoin) {
        this.allowJoin = allowJoin;
    }

    public void setResponds(boolean responds) {
        this.responds = responds;
    }
}
