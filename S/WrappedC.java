package S;

import java.util.Observable;
import java.util.Observer;

public class WrappedC implements Observer {
    private RemoteC remoteC;

    public WrappedC (RemoteC remoteC) {
        this.remoteC = remoteC;
    }

    public void update (Observable remoteS, Object arg) {
        //@TODO: Call the remote method on C to send the HTML page
    }
}