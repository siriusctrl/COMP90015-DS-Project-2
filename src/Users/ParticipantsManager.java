package Users;

import Board.ParticipantListPanel;
import Feedback.*;
import RMI.IRemoteBoard;
import Tools.*;
import Utils.UserType;

import static Utils.Logger.*;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ParticipantsManager {

    private Server server;
    // clientID:clientRMI
    private HashMap<String, IRemoteBoard> clients;
    private HashMap<String, IRemoteBoard> waitingList;

    private ParticipantListPanel participantListPanel = new ParticipantListPanel(null, this);

    public ParticipantsManager(Server server) {
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

        participantListPanel.updateList();

        // todo : below are trying to mock the gui request by suspending, remove when gui is set

        Thread gui = new Thread(() -> server.reloadWaitingList());
        gui.start();


        return new Feedback(FeedbackState.SUCCEED, "Waiting for server approval");
    }


    /**
     * Allow the user to join the whiteboard, this will only be invoked by GUI/server
     * @param userId the client id
     * @throws RemoteException
     */
    public void allowJoin(String userId) {

        try {
            IRemoteBoard clientBoard = waitingList.get(userId);
            clientBoard.allowJoin();
            clients.put(userId, clientBoard);
        } catch (RemoteException e) {
            logError("Unable to join remote user: " + userId + ", the user might quit already.");
        }

        waitingList.remove(userId);
        participantListPanel.updateList();
    }

    /**
     * Reject the user join request, this will only be invoked by GUI/server
     * @param userId the client id
     * @throws RemoteException
     */
    public void rejectJoin(String userId) {
        try {
            IRemoteBoard clientBoard = waitingList.get(userId);
            clientBoard.rejectJoin();
        } catch (RemoteException e) {
            logError("Unable to reject remote user: " + userId + ", the user might quit already.");
        }

        waitingList.remove(userId);
        participantListPanel.updateList();
    }

    public void kick(String userId) {
        try {
            IRemoteBoard clientBoard = clients.get(userId);
            clientBoard.kickOut();
        } catch (RemoteException e) {
            logError("Unable to kick remote user: " + userId + ", the user might quit already.");
        }

        clients.remove(userId);
        participantListPanel.updateList();
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
        newBoard.add(new Line(new Point(0, 0), new Point(0,1)));

        clientBoard.updateBoard(newBoard);
    }

    public UserType getUserType(String uid) {
        // todo : fix this
        return UserType.HOST;
    }

    public void setParticipantList(ParticipantListPanel panel) {
        this.participantListPanel = panel;
    }

    public String getServerId() {
        return server.getUid();
    }

    public boolean isHost() {
        // todo: make this manager to both host and participants
        return true;
    }

    public Set<String> getAllParticipants() { return clients.keySet(); }

    public Set<String> getAllWaiting() { return waitingList.keySet(); }
}
