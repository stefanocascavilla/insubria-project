import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry = LocateRegistry.getRegistry(args[0], 1099);
        RemoteS server = (RemoteS) registry.lookup("sObj");
        Thread thread = null;

        for (int x = 0; x < 5; x++) {
            thread = new Thread(new RemoteCImpl("thread_" + x, server, x + 1));
            thread.start();
        }
    }
}