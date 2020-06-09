import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteS extends Remote {
    public boolean addObserver (RemoteC observer) throws RemoteException;

    public boolean loadPage (String url, String pageContent) throws RemoteException;
}