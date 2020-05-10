package RMI;

import Client.Client;
import Tools.Drawable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class RemoteBoard extends UnicastRemoteObject implements IRemoteBoard {

    private Client client;

    public RemoteBoard(Client client) throws RemoteException {
        this.client = client;
    }

    @Override
    public void allowJoin() {
        client.setResponds(true);
        client.setAllowJoin(true);
    }

    @Override
    public void rejectJoin() {
        client.setResponds(true);
        client.setAllowJoin(false);
    }

    @Override
    public void updateBoard(Vector<Drawable> paints) {
        // todo : finish this when board is set
        for (Drawable d: paints) {
            System.out.println("History said: " + d.msg);
        }
    }
}
