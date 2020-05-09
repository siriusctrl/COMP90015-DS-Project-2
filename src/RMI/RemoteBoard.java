package RMI;

import Tools.Drawable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class RemoteBoard extends UnicastRemoteObject implements IRemoteBoard {

    public RemoteBoard() throws RemoteException {
    }

    @Override
    public void updateBoard(Vector<Drawable> paints) {
        // todo : finish this when board is set
        for (Drawable d: paints) {
            System.out.println("History said" + d.msg);
        }
    }
}
