import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

/**
* Classe che genera i vari Thread che andranno a scaricare il contenuto della pagina WEB,
* avente URL uguale a quello passato come parametro al singolo Thread
*/
public class F {
    // numero massimo di Thread da generare
    private static final int MAX_THREADS_NUMBER = 2;

    /**
    * @param args URLs dei vari siti WEB da visitare (max 2 - uno per ogni Thread) e l'IP del server remoto con cui connettersi
    */
    public static void main(String[] args) {
        // se ci sono almeno due valori passati come parametro (un URL e l'indirizzo statico del server)
        // allora continua l'esecuzione
        if(args.length >= 2) {
            System.setSecurityManager(new RMISecurityManager());
            try {
                // ottengo il riferimento dell'oggetto remoto che descrive il server
                Registry registro = LocateRegistry.getRegistry(args[args.length - 1], 1099);
                RemoteS stub = (RemoteS) registro.lookup("sObj");

                // creo un numero di Threads pari al numero limite contenuto in MAX_THREADS_NUMBER
                for(int i = 0; i < MAX_THREADS_NUMBER; i++) {
                    new SingleF(i, stub, args.length - 1 == MAX_THREADS_NUMBER ? args[i] : args[0]);
                    // dopo aver creato un nuovo Thread, simulo una breve pausa
                    // per dare tempo ai vari Observers nel Back End di notare la differenza fra i due URLs
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.err.println("An Exception occurred: " + e.toString());
            }
        } else {
            System.err.println("You must insert an URL website and the IP of the server at least");
        }
    }
}