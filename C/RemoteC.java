import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteC extends Remote {
    public void sendPage (WebsiteInfo info) throws RemoteException;
}