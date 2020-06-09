import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* Interfaccia implementata dalla classe RemoteSImpl.
*/
public interface RemoteS extends Remote {
    /**
    * Metodo chiamato dalle istanze di tipo RemoteC per essere registrate come Observer (abbonarsi al servizio)
    * nei confronti dell'oggetto remoto di tipo RemoteS.
    *
    * @param observer identifica l'istanza dell'oggetto Observer.
    * @param name identifica il nome (identificatore) dell'oggetto.
    */
    public void subscribe (RemoteC observer, String name) throws RemoteException;

    /**
    * Metodo chiamato dalle istanze di tipo RemoteC per essere cancellate come Observer (eliminare l'abbonamento)
    * nei confronti dell'oggetto remoto di tipo RemoteS.
    *
    * @param name identifica l'identificativo dell'oggetto Observer (necessario per trovare il riferimento di tipo WrappedC - dato che
    * l'Observer viene materializzato con oggetti di tipo WrappedC) per cancellarlo dalla lista di Observer e da listObservers.
    */
    public void unsubscribe (String name) throws RemoteException;

    /**
    * Metodo chiamato dalle istanze di tipo SingleF (thread) per ricevere le info relative alla pagina web letta da SingleF.
    *
    * @param info identifica le info lette da SingleF (sono due: url e corpo della pagina HTML).
    */ 
    public void loadPage (WebsiteInfo info) throws RemoteException;
}