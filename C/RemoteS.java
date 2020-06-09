import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteS extends Remote {
    public void subscribe (RemoteC observer, String name) throws RemoteException;

    public void unsubscribe (String name) throws RemoteException;

    public void loadPage (WebsiteInfo info) throws RemoteException;
}