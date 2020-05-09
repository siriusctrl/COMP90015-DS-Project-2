package RMI;

import Server.ClientsManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteJoin extends UnicastRemoteObject implements IRemoteJoin {

    private ClientsManager clientsManager;

    public RemoteJoin(ClientsManager clientsManager) throws RemoteException {
        this.clientsManager = clientsManager;
    }

    @Override
    public boolean join(String userId, String host, int port) throws RemoteException, NotBoundException {
        System.out.println("==== get a user ====");
        System.out.println("userid = " + userId + ", host = " + host + ", port = " + port);
        // clientsManager add clients
        return clientsManager.addUser(userId, host, port);
    }
}
