package RMI;

import Client.Client;
import Tools.Drawable;

import javax.swing.plaf.TableHeaderUI;
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
        client.invokeBoard();
    }

    @Override
    public void rejectJoin() {
        System.out.println("Join request reject by server");
        client.exit();
    }

    @Override
    public void updateBoard(Vector<Drawable> paints) {
        // todo : finish this when board is set
    }

    public void serverClosed() {
        client.exit();
    }
}
