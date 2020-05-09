package Server;

import RMI.CreateRegistry;
import RMI.RemoteHello;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMI.RemoteHello;
import RMI.RemoteJoin;

public class Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        // create one registry at local host
        CreateRegistry.getRegistry(3456);
        // get registry from local host
        Registry registry = LocateRegistry.getRegistry(3456);

        ClientsManager clientsManager = new ClientsManager();

        registry.bind("hello", new RemoteHello());
        registry.bind("join", new RemoteJoin(clientsManager));
    }
}
