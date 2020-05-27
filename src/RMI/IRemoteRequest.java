package RMI;

import Utils.Feedback;
import Tools.Drawable;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Vector;

public interface IRemoteRequest extends Remote {
    /**
     * Handle new client join request
     * @param clientId client unique id
     * @param host host ip address
     * @param port client port that registry bind to
     * @return Feedback for results handling
     * @throws RemoteException
     * @throws NotBoundException
     */
    Feedback joinRequest(String clientId, String host, int port) throws RemoteException, NotBoundException;

    /**
     * get the list all participants, participant in waiting list will only be visible to host
     * @return
     * @throws RemoteException
     */
    HashSet<String> getParticipantList() throws RemoteException;

    /**
     * remove user from list, only invoke by the client to remove itself
     * @param uid the one you want to remove
     * @throws RemoteException
     */
    void removeUserRequest(String uid) throws RemoteException;

    /**
     * get board history
     * @return board history vector
     * @throws RemoteException
     */
    Vector<Drawable> getHistoryRequest() throws RemoteException;

    /**
     * add drawable to board, only invoked where client add new shapes
     * @param drawable the new drawable
     * @throws RemoteException
     */
    void addDrawableRequest(Drawable drawable) throws RemoteException;
}
