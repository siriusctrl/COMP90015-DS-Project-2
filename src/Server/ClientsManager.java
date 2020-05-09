package Server;

import Client.Client;
import RMI.IRemoteBoard;
import RMI.RemoteBoard;
import Tools.Drawable;
import Tools.Line;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ClientsManager {
    // clientID:clientRMI
    private HashMap<String, IRemoteBoard> clients;

    public ClientsManager() {
        clients = new HashMap<>();
    }

    public boolean addUser(String userId, String ip, int port) throws RemoteException, NotBoundException {
        if(clients.containsKey(userId)) {
            return false;
        }

        Registry clientRegistry = LocateRegistry.getRegistry(ip, port);
        IRemoteBoard clientBoard = (IRemoteBoard) clientRegistry.lookup("board");

        clients.put(userId, clientBoard);

        // todo : test start from here

        updateBoard(userId);

        // test end here

        return true;
    }

    public void updateBoard(String userId) throws RemoteException {
        IRemoteBoard clientBoard = clients.get(userId);

        // todo : below are only for testing, edit later if needed
        Vector<Drawable> newBoard = new Vector<>();
        newBoard.add(new Line());

        clientBoard.updateBoard(newBoard);
    }
}
