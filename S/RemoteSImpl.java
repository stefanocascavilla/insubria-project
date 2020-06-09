import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;
import java.util.Observable;

/**
* Classe che rappresenta il server sotto forma di oggetto remoto opportunamente registrato nel RMI registry.
*/
public class RemoteSImpl extends Observable implements RemoteS {
    private HashMap<String, WrappedC> listObservers;

    public RemoteSImpl () throws RemoteException {
        this.listObservers = new HashMap<String, WrappedC>();
    }

    /**
    * Metodo chiamato dalle istanze di tipo RemoteC per essere registrate come Observer (abbonarsi al servizio)
    * nei confronti dell'oggetto remoto di tipo RemoteS.
    *
    * @param observer identifica l'istanza dell'oggetto Observer.
    * @param name identifica il nome (identificatore) dell'oggetto.
    */
    public synchronized void subscribe (RemoteC observer, String name) throws RemoteException {
        WrappedC wrappedC = new WrappedC(observer, name);
        this.addObserver(wrappedC);
        this.listObservers.put(name, wrappedC);
        System.out.println("Aggiunto observer: " + name);
    }

    /**
    * Metodo chiamato dalle istanze di tipo RemoteC per essere cancellate come Observer (eliminare l'abbonamento)
    * nei confronti dell'oggetto remoto di tipo RemoteS.
    *
    * @param name identifica l'identificativo dell'oggetto Observer (necessario per trovare il riferimento di tipo WrappedC - dato che
    * l'Observer viene materializzato con oggetti di tipo WrappedC) per cancellarlo dalla lista di Observer e da listObservers.
    */
    public synchronized void unsubscribe (String name) throws RemoteException {
        WrappedC observerToDelete = this.listObservers.remove(name);
        this.deleteObserver(observerToDelete);
        System.out.println("Rimosso observer: " + name);
    }

    /**
    * Metodo chiamato dalle istanze di tipo SingleF (thread) per ricevere le info relative alla pagina web letta da SingleF.
    *
    * @param info identifica le info lette da SingleF (sono due: url e corpo della pagina HTML).
    */
    public synchronized void loadPage (WebsiteInfo info) throws RemoteException {
        System.out.println("Invio dell'informazione ricevuta...");
        this.setChanged();
        this.notifyObservers(info);
        System.out.println("Invio dell'informazione avvenuta con successo!");
    }

    public static void main (String[] args) throws RemoteException {
        if (System.getSecurityManager() == null) {
            System.out.println("Configurazione Security Manager...");
            System.setSecurityManager(new RMISecurityManager());
        }

        RemoteSImpl server = new RemoteSImpl();
        Registry registry = LocateRegistry.createRegistry(1099);

        RemoteS stub = (RemoteS) UnicastRemoteObject.exportObject(server, 3939);
        registry.rebind("sObj", stub);
        System.out.println("Server in ascolto...");
    }
}