import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

/**
* Classe che funge da wrapper per gli oggetti di tipo RemoteC per gestire il pattern Observable/Observer.
* In questo caso la classe WrapperC è Observer nei confronti del server (implementato con la classe RemoteSImpl)
*/
public class WrappedC implements Observer {
    private RemoteC remoteC;
    private String name;

    public WrappedC (RemoteC remoteC, String name) {
        this.remoteC = remoteC;
        this.name = name;
    }

    /**
     * Metodo chiamato nel momento in cui il server (RemoteSImpl) chiama notifyObservers() per avvisare gli observer di un
     * cambiamento avvenuto.
     * 
     * @param remoteS identifica l'oggetto osservato che ha chiamato il metodo notifyObservers()
     * @param arg identifica l'argomento con cui l'oggetto osservato ha chiamato il metodo notifyObservers()
     */
    public void update (Observable remoteS, Object arg) {
        try {
            this.remoteC.sendPage((WebsiteInfo) arg);
        } catch (RemoteException exc) {
            RemoteSImpl remoteServer = (RemoteSImpl) remoteS;
            System.out.println("Errore durante l'invio delle info all'oggetto remoto C: " + exc);
            try {
                remoteServer.unsubscribe(name);
            } catch (RemoteException excInternal) {}
        }
    }
}