import java.net.*;
import java.io.*;

/**
* Classe utilizzata per scaricare la pagina HTML di una pagina WEB in base all'URL passato nel construttore
* e che permette di astrarre la parte di connessione e ricezione dei dati
*/
public class Proxy implements ProxyInterface {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    /**
    * Costruttore della classe Proxy
    *
    * @param url URL della pagina WEB da scaricare
    */
    public Proxy(String url) {
        try {
            URI uri = new URI(url);
            InetAddress addr = InetAddress.getByName(uri.getHost());
            // creo una nuova istanza della classe Socket passando l'host e la relativa a cui connettersi
            socket = new Socket(addr, ProxyInterface.PORT);
            // creo gli stream di input e output
            reader = new BufferedReader(
                        new InputStreamReader(
                            socket.getInputStream()));
            writer = new PrintWriter(
                        new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
        } catch(URISyntaxException exc) {
            System.err.println("ERROR the URL passed as argument is wrong");
        } catch(UnknownHostException exc) {
            System.err.println("ERROR the host is unknown");
        } catch(IOException exc) {
            System.err.println("ERROR while reading");
        } catch(Exception exc) {
            exc.printStackTrace();
            System.err.println("ERROR => generic error");
        }
    }

    /**
    * Metodo che restituisce il contenuto HTML della pagina il cui URL è stato passato come parametro
    *
    * @param url URL della pagina WEB da scaricare
    */
    public String setWebPageByProxy(String url) {
        String firstLineHTMLPage = "";

        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getRawPath();
            // Effettuo la richiesta per ottenere il contenuto HTML della pagina
            writer.print(
                "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n\r\n"
            );
            // pulisco residui
            writer.flush();
            
            String line;
            // leggo tutta la pagina aggiornando il contenuto di firstLineHTMLPage (che conterrà l'intera pagina)
            while ((line = reader.readLine()) != null) {
                firstLineHTMLPage += line + "\n";
            }

        } catch(URISyntaxException exc) {
            System.err.println("ERROR the URL passed as argument is wrong");
        } catch(UnknownHostException exc) {
            System.err.println("ERROR the host is unknown");
        } catch(IOException exc) {
            System.err.println("ERROR while reading");
        } finally {
            System.out.println("*** closing...");
            try {
                // se la socket non è stata inizializzata o se è già stata chiusa, chiudo la connessione
                if(socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch(IOException exc) {
                System.err.println("ERROR error while closing socket");
            }
            // restituisco il valore della pagina HTML
            return firstLineHTMLPage;
        }
    }
}