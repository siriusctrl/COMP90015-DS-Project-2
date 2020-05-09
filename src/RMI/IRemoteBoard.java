package RMI;

import Tools.Drawable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IRemoteBoard extends Remote {
    void updateBoard(Vector<Drawable> paints) throws RemoteException;
}
