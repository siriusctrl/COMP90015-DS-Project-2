package RMI;

import Feedback.Feedback;
import Server.ParticipantsManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteRequest extends UnicastRemoteObject implements IRemoteRequest {

    private ParticipantsManager participantsManager;

    public RemoteRequest(ParticipantsManager participantsManager) throws RemoteException {
        this.participantsManager = participantsManager;
    }

    @Override
    public Feedback joinRequest(String userId, String host, int port) {
        System.out.println("==== get a user ====");
        System.out.println("userid = " + userId + ", host = " + host + ", port = " + port);
        // participantsManager add clients
        return participantsManager.addUserToWaitingList(userId, host, port);
    }
}
