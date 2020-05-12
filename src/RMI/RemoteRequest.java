package RMI;

import Feedback.Feedback;
import Users.ParticipantsManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

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

    @Override
    public Set<String> getParticipantList() {
        return participantsManager.getAllParticipantsID();
    }
}
