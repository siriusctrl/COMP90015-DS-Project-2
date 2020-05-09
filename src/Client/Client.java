package Client;

import RMI.*;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
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
}
