import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* Interfaccia implementata dalla classe RemoteCImpl.
*/
public interface RemoteC extends Remote {
    /**
    * Metodo chiamato sulle istanze di tipo RemoteC dal server (di tipo RemoteS) per inviare i dati relativi
    * alle pagine web ricevuti dalle istanze di tipo SingleF.
    *
    * @param info identifica le info lette da SingleF (sono due: url e corpo della pagina HTML).
    */
    public void sendPage (WebsiteInfo info) throws RemoteException;
}