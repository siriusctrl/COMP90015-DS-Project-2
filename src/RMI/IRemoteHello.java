package RMI;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteHello extends Remote {

    String hello(String text) throws RemoteException;
}
