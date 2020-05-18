package RMI;

import Tools.Drawable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IRemoteBoard extends Remote {
    void allowJoin(String HostId) throws RemoteException;
    void rejectJoin() throws RemoteException;
    void kickOut() throws RemoteException;
    void HostQuit() throws RemoteException;
    void refreshParticipantList() throws RemoteException;
    void repaint() throws RemoteException;
    void notification(String text) throws RemoteException;
}
