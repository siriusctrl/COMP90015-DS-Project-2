package RMI;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteJoin extends Remote {
    boolean join(String clientId, String host, int port) throws RemoteException, NotBoundException;
}
