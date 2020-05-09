package RMI;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CreateRegistry {

    public static int getRegistryPort() throws IOException {
        ServerSocket s = new ServerSocket(0);
        int unusedPort = s.getLocalPort();
        s.close();

        return unusedPort;
    }


    public static Registry getRegistry(int port) throws RemoteException {

        return LocateRegistry.createRegistry(port);
    }

}
