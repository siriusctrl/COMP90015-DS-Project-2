package RMI;

import Feedback.Feedback;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IRemoteRequest extends Remote {
    Feedback joinRequest(String clientId, String host, int port) throws RemoteException, NotBoundException;
    Set<String> getParticipantList() throws RemoteException;
}
