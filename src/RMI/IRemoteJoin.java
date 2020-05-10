package RMI;

import Feedback.Feedback;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteJoin extends Remote {
    Feedback join(String clientId, String host, int port) throws RemoteException, NotBoundException;
}
