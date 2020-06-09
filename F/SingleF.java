import java.rmi.RemoteException;

public class SingleF extends Thread {
  private int id;
  private String URLWebsite;
  RemoteS stub;

  public SingleF(int id, RemoteS stub, String URLWebsite) { 
    this.id = id;
    this.URLWebsite = URLWebsite;
    this.stub = stub;
    System.out.println("Thread" + id + " created");
    this.start();
  }

  public void run() {
    try {
      System.out.println("Thread" + id + " running");
      Proxy proxy = new Proxy(URLWebsite);
      String webPageContent = proxy.setWebPageByProxy(URLWebsite);
      if(webPageContent != null) {
        WebsiteInfo contentSite = new WebsiteInfo(URLWebsite, webPageContent);
        System.out.println("Thread" + id + " received the HTML page");
        try {
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