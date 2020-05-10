package Server;

import Feedback.*;
import RMI.IRemoteBoard;
import Tools.Drawable;
import Tools.Line;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientsManager {

    private Server server;
    // clientID:clientRMI
    private HashMap<String, IRemoteBoard> clients;
    private HashMap<String, IRemoteBoard> waitingList;

    public ClientsManager(Server server) {
        clients = new HashMap<>();
        waitingList = new HashMap<>();
        this.server = server;
    }

    /**
     * Add a request user to waiting list and wait for server manager to approve, and return
     * a Feedback to the client
     * @param userId identical Id that will be display on board
     * @param ip request user ip address
     * @param port client RMI port number
     * @return Feedback about either SUCCEED or ERROR
     */
    public Feedback addUserToWaitingList(String userId, String ip, int port) {
        IRemoteBoard clientBoard;

        if(clients.containsKey(userId) || waitingList.containsKey(userId)) {
            return new Feedback(FeedbackState.ERROR, "Duplicate userId, please try another one");
        }

        try {
            Registry clientRegistry = LocateRegistry.getRegistry(ip, port);
            clientBoard = (IRemoteBoard) clientRegistry.lookup("board");
            waitingList.put(userId, clientBoard);
        } catch (RemoteException | NotBoundException e) {
            return new Feedback(FeedbackState.ERROR, e.getMessage());
        }

        // todo : below are trying to mock the gui request by suspending, remove when gui is set

        server.reloadWaitingList();

        return new Feedback(FeedbackState.SUCCEED, "Waiting for server approval");
    }


    /**
     * Allow the user to join the whiteboard, this will only be invoked by GUI/server
     * @param userId the client id
     * @throws RemoteException
     */
    public void allowJoin(String userId) throws RemoteException {
        IRemoteBoard clientBoard = waitingList.get(userId);
        waitingList.remove(userId);
        clientBoard.allowJoin();
        clients.put(userId, clientBoard);
    }

    /**
     * Reject the user join request, this will only be invoked by GUI/server
     * @param userId the client id
     * @throws RemoteException
     */
    public void rejectJoin(String userId) throws RemoteException {
        IRemoteBoard clientBoard = waitingList.get(userId);
        clientBoard.rejectJoin();
        waitingList.remove(userId);
    }

    /**
     * update a specific user's board the changes to all the clients
     * @param userId
     * @throws RemoteException
     */
    public void updateBoard(String userId) throws RemoteException {
        IRemoteBoard clientBoard = clients.get(userId);

        // todo : below are only for testing, change it to real board history later
        Vector<Drawable> newBoard = new Vector<>();
        newBoard.add(new Line());

        clientBoard.updateBoard(newBoard);
    }

    public Set<String> getAllWaiting() {
        return waitingList.keySet();
    }
}
