package Users;

import Board.ParticipantListPanel;
import Feedback.*;
import RMI.IRemoteBoard;
import RMI.IRemoteRequest;
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

    // clientID:clientRMI
    private HashMap<String, IRemoteBoard> allParticipants;
    private HashMap<String, IRemoteBoard> waitingList;

    private UserType mode;
    private String currentUid;
    private String hostId;

    private IRemoteRequest hostReq;

    private ParticipantListPanel participantListPanel;

    public ParticipantsManager(UserType mode, String uid) {
        allParticipants = new HashMap<>();
        waitingList = new HashMap<>();
        this.mode = mode;
        this.currentUid = uid;

        if (mode == UserType.HOST) {
            hostId = uid;
        }
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

        if(allParticipants.containsKey(userId) || waitingList.containsKey(userId)) {
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
//
//        // todo : below are trying to mock the gui request by suspending, remove when gui is set
//
//        Thread gui = new Thread(() -> server.reloadWaitingList());
//        gui.start();


        return new Feedback(FeedbackState.SUCCEED, "Waiting for server approval");
    }


    /**
     * Allow the user to join the whiteboard, this will only be invoked by GUI/server
     * @param userId the client id
     * @throws RemoteException
     */
    public void allowJoin(String userId) {
        if (mode != UserType.HOST) {
            return;
        }

        try {
            IRemoteBoard clientBoard = waitingList.get(userId);
            clientBoard.allowJoin(currentUid);
            allParticipants.put(userId, clientBoard);
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
        if (mode != UserType.HOST) {
            return;
        }

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
        if (mode != UserType.HOST) {
            return;
        }

        try {
            IRemoteBoard clientBoard = allParticipants.get(userId);
            clientBoard.kickOut();
        } catch (RemoteException e) {
            logError("Unable to kick remote user: " + userId + ", the user might quit already.");
        }

        allParticipants.remove(userId);
        participantListPanel.updateList();
    }

    /**
     * update a specific user's board the changes to all the allParticipants
     * @param userId
     * @throws RemoteException
     */
    public void updateBoard(String userId) throws RemoteException {
        IRemoteBoard clientBoard = allParticipants.get(userId);

        // todo : below are only for testing, change it to real board history later
        Vector<Drawable> newBoard = new Vector<>();
        newBoard.add(new Line(new Point(0, 0), new Point(0,1)));

        clientBoard.updateBoard(newBoard);
    }


    public UserType getUserType(String uid) {
        if (uid.equals(hostId)) {
            return UserType.HOST;
        } else if (waitingList.containsKey(uid)) {
            return UserType.VISITOR;
        } else if (allParticipants.containsKey(uid)) {
            return UserType.PARTICIPANT;
        } else {
            logError("Unknown uid: " + uid);
            System.exit(1);
        }

        return null;
    }

    public void setParticipantList(ParticipantListPanel panel) {
        this.participantListPanel = panel;
    }

    public String getServerId() {
        return hostId;
    }

    public boolean isHost() {
        return currentUid.equals(hostId);
    }

    public boolean isHost(String uid) {
        return uid.equals(hostId);
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Set<String> getAllParticipantsID() {
        if(mode == UserType.HOST) {
            return allParticipants.keySet();
        }

        try {
            return hostReq.getParticipantList();
        } catch (RemoteException e) {
            logError("Server Failed");
            System.exit(0);
        }

        return null;
    }

    public void setHostReq(IRemoteRequest hostReq) {
        this.hostReq = hostReq;
    }

    public Set<String> getAllWaitingID() {
        if (mode == UserType.HOST) {
            return waitingList.keySet();
        }

        return new HashSet<>();
    }

}
