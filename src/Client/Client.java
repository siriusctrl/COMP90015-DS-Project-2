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

    public static void main(String[] args) {
        try {
            Registry serverRegistry = LocateRegistry.getRegistry("localhost", 3456);

            IRemoteHello remoteHello = (IRemoteHello) serverRegistry.lookup("hello");
            System.out.println("Said: Yes");
            String echo = remoteHello.hello("Yes");
            System.out.println("Received: " + echo);

            // create client RMI for board updating
            int selfPort = CreateRegistry.getRegistryPort();

            Registry selfRegistry = LocateRegistry.createRegistry(selfPort);
            System.out.println("RMI at " + selfPort);
            selfRegistry.bind("board", new RemoteBoard());


            // try to join the remote server
            IRemoteJoin remoteJoin = (IRemoteJoin) serverRegistry.lookup("join");

            remoteJoin.join("morry", "localhost", selfPort);

        } catch (NotBoundException | IOException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public Client() {
        try {
            this.selfPort = CreateRegistry.getRegistryPort();
            this.selfRegistry = LocateRegistry.createRegistry(selfPort);
            selfRegistry.bind("board", new RemoteBoard());
        } catch (IOException | AlreadyBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Feedback join() {
        getServerRegistry();

        try {
            IRemoteJoin remoteJoin = (IRemoteJoin) serverRegistry.lookup("join");
            return remoteJoin.join(userId, serverIp, selfPort);
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

    public boolean isHelp() {
        return help;
    }
}
