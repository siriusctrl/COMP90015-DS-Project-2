package RMI;

import Feedback.Feedback;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;

public interface IRemoteRequest extends Remote {
    Feedback joinRequest(String clientId, String host, int port) throws RemoteException, NotBoundException;
    HashSet<String> getParticipantList() throws RemoteException;
    void removeUserRequest(String uid) throws RemoteException;
}
