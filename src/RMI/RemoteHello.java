package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteHello extends UnicastRemoteObject implements IRemoteHello{

    public RemoteHello() throws RemoteException {
    }

    @Override
    public String hello(String text) throws RemoteException {
        return "Not Hello, " + text;
    }
}
