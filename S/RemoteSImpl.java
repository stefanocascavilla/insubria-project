package S;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;
import java.util.Observable;

public class RemoteSImpl extends Observable implements RemoteS {
    private HashMap<String, WrappedC> listObservers;

    public RemoteSImpl () throws RemoteException {
        this.listObservers = new HashMap<String, WrappedC>();
    }

    public void subscribe (RemoteC observer, String name) throws RemoteException {
        WrappedC wrappedC = new WrappedC(observer);
        this.addObserver(wrappedC);
        this.listObservers.put(name, wrappedC);
        System.out.println("Aggiunto observer: " + name);
    }

    public void unsubscribe (String name) throws RemoteException {
        WrappedC observerToDelete = this.listObservers.remove(name);
        this.deleteObserver(observerToDelete);
        System.out.println("Rimosso observer: " + name);
    }

    public void loadPage (WebsiteInfo info) throws RemoteException {
        System.out.println("Invio dell'informazione ricevuta...");
        this.setChanged();
        this.notifyObservers(info);
        System.out.println("Invio dell'informazione avvenuta con successo!");
    }

    public static void main (String[] args) throws RemoteException {
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