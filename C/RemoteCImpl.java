import java.io.Serializable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Random;

public class RemoteCImpl extends UnicastRemoteObject implements RemoteC, Serializable, Runnable {
    private static final long serialVersionUID = 1L;

    private String threadId;
    transient private RemoteS server;
    private Random random;

    public RemoteCImpl (String threadId, RemoteS server) throws RemoteException {
        this.threadId = threadId;
        this.server = server;

        this.random = new Random();
    }

    public void sendPage (WebsiteInfo info) throws RemoteException {
        System.out.println("Thread_" + this.threadId + " Pagina ricevuta con successo!");
        System.out.println("URL pagina: " + info.getUrl());
        System.out.println("Corpo pagina: " + info.getWebPageContentHTML());
    }

    public void run () {
        try {
            this.server.subscribe(this, this.threadId);
            System.out.println("Thread_" + this.threadId + " registrato");

            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException exc) {
                    System.out.println("Thread " + this.threadId + " terminato");
                    break;
                }
                if (this.random.nextInt(2) == 0) {
                    this.server.unsubscribe(this.threadId);
                    break;
                }
            }
        } catch (RemoteException exc) {
            System.out.println("Remote exception occurred for thread " + this.threadId + " : " + exc);
        }
    }
}