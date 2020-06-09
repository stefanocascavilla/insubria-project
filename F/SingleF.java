import java.rmi.RemoteException;

/** 
* Classe che rappresenta un singolo Thread che scarica la pagina WEB 
* il cui URL è stato passato nel construttore.
*/
public class SingleF extends Thread {
  private int id;
  private String URLWebsite;
  RemoteS stub;

  /**
  * Costruttore della classe SingleF
  *
  * @param id Identifica un'istanza di questa classe
  * @param stub Rappresenta il riferimento dell'istanza del server remoto all'interno del Registry
  * @param URLWebsite Rappresenta l'URL della pagina WEB di cui si vuole ottenere il codice HTML
  */
  public SingleF(int id, RemoteS stub, String URLWebsite) { 
    this.id = id;
    this.URLWebsite = URLWebsite;
    this.stub = stub;
    // Scritta di debug per visualizzare lo stato di avanzamento del singolo Thread
    System.out.println("Thread" + id + " created");
    // il Thread passa allo stato di ready
    this.start();
  }

  /**
  * Metodo utilizzato dal Thread nello stato di running
  */
  public void run() {
    try {
      System.out.println("Thread" + id + " running");
      // crea una nuova istanza della classe Proxy (ogni Thread ha la sua istanza di Proxy)
      Proxy proxy = new Proxy(URLWebsite);
      // ottenimento del codice della pagina HTML del sito all'URL specificato
      String webPageContent = proxy.setWebPageByProxy(URLWebsite);
      // se il contenuto della pagina HTML ottenuto non è nullo, invia i dati al server remoto
      if(webPageContent != null) {
        // crea un'istanza della classe WebsiteInfo per contenere sia URL che la pagina HTML ottenuta
        WebsiteInfo contentSite = new WebsiteInfo(URLWebsite, webPageContent);
        System.out.println("Thread" + id + " received the HTML page");
        try {
          // invia l'oggetto di tipo WebsiteInfo contenente URL e contenuto HTML della pagina al server
          stub.loadPage(contentSite);
          System.out.println("Thread" + id + " called loadPage from RemoteSImpl");
        } catch (Exception e) {
          System.err.println("Client exception: " + e.toString());
        }
      } else {
          System.err.println("The content of the web page couldn't been retrieved");
      }
    } catch(ArrayIndexOutOfBoundsException exc) {
        System.err.println("No parameter were passed");
    }
  }
}