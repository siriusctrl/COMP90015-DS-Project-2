package RMI;

import Feedback.Feedback;
import Server.ClientsManager;
import Tools.Drawable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteRequest extends UnicastRemoteObject implements IRemoteRequest {

    private ClientsManager clientsManager;

    public RemoteRequest(ClientsManager clientsManager) throws RemoteException {
        this.clientsManager = clientsManager;
    }

    @Override
    public Feedback joinRequest(String userId, String host, int port) {
        System.out.println("==== get a user ====");
        System.out.println("userid = " + userId + ", host = " + host + ", port = " + port);
        // clientsManager add clients
        return clientsManager.addUserToWaitingList(userId, host, port);
    }

    @Override
    public void addDrawableRequests(Drawable drawable) {
        // todo : implement this so that the client can send addDrawable Request to server
    }

    public void leave(String userId) {
        clientsManager.removeUser(userId);
    }

}
