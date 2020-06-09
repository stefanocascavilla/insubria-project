import java.io.Serializable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Random;

/**
* Classe le cui istanze (thread) fungeranno da abbonati nei confronti del server di tipo RemoteS per ricevere le info delle
* pagine web lette dagli oggetti di tipo SingleF.
*/
public class RemoteCImpl implements RemoteC, Serializable, Runnable {
    private static final long serialVersionUID = 1L;

    private String threadId;
    transient private RemoteS server;
    private Random random;

    public RemoteCImpl (String threadId, RemoteS server, int portCounter) throws RemoteException {
        this.threadId = threadId;
        this.server = server;

        this.random = new Random();

        UnicastRemoteObject.exportObject(this, portCounter);
    }

    /**
    * Metodo chiamato sulle istanze di tipo RemoteC dal server (di tipo RemoteS) per inviare i dati relativi
    * alle pagine web ricevuti dalle istanze di tipo SingleF.
    *
    * @param info identifica le info lette da SingleF (sono due: url e corpo della pagina HTML).
    */
    public void sendPage (WebsiteInfo info) throws RemoteException {
        System.out.println("Thread_" + this.threadId + " Pagina ricevuta con successo!");
        System.out.println("URL pagina: " + info.getUrl());
        System.out.println("Corpo pagina: " + info.getWebPageContentHTML());
    }

    /**
    * Metodo che rappresenta il corpo del thread. Il thread all'inizio si "abbona" al servizio del server di tipo RemoteS. Nel mentre,
    * se il server riceve delle info dalle istanze di tipo SingleF, verrà richiamao il metodo sendPage. Ogni 10 secondi, il thread
    * genera un valore tra 0 e 1 casuale e, nel caso in cui fosse 0, elimina il suo "abbonamento" dal server e termina.
    */
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
                    System.out.println("Thread " + this.threadId + " terminato");
                    break;
                }
            }
            UnicastRemoteObject.unexportObject(this, true);
        } catch (RemoteException exc) {
            System.out.println("Remote exception per il thread " + this.threadId + " : " + exc);
        }
    }
}