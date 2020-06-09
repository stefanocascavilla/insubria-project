import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Observable;

public class RemoteSImpl extends Observable implements RemoteS {
    public RemoteSImpl () throws RemoteException {}

    public boolean addObserver (RemoteC observer) throws RemoteException {
        WrappedC wrappedC = new WrappedC(observer);
        this.addObserver(wrappedC);
        System.out.println("Aggiunto observer: " + wrappedC);
    }

    public static void main (String[] args) {
        if (System.getSecurityManager() == null) { 
            System.setSecurityManager(new RMISecurityManager()); 
        }

        RemoteSImpl server = new RemoteSImpl();
        Registry registry = LocateRegistry.createRegistry(1099);

        RemoteS stub = (RemoteS) UnicastRemoteObject.exportObject(server, 3939);
        registry.rebind("sObj", stub);
        System.out.println("Server in ascolto...");
    }
}