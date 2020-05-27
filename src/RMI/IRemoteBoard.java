package RMI;

import Tools.Drawable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IRemoteBoard extends Remote {
    /**
     * allow a user to join the board
     * @param HostId host name
     * @throws RemoteException
     */
    void allowJoin(String HostId) throws RemoteException;

    /**
     * reject join request by host
     * @throws RemoteException
     */
    void rejectJoin() throws RemoteException;

    /**
     * kick out from board by host
     * @throws RemoteException
     */
    void kickOut() throws RemoteException;

    /**
     * host quit the board
     * @throws RemoteException
     */
    void HostQuit() throws RemoteException;

    /**
     * refresh the list as new client joined the board
     * @throws RemoteException
     */
    void refreshParticipantList() throws RemoteException;

    /**
     * repaint the board as there are new things there
     * @throws RemoteException
     */
    void repaint() throws RemoteException;

    /**
     * notify the client if any critical changes made by host
     * @param text the context
     * @throws RemoteException
     */
    void notification(String text) throws RemoteException;
}
