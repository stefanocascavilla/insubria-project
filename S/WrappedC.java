import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public class WrappedC implements Observer {
    private RemoteC remoteC;
    private String name;

    public WrappedC (RemoteC remoteC, String name) {
        this.remoteC = remoteC;
        this.name = name;
    }

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