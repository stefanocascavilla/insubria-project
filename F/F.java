import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class F {
    private 

    public static void main(String[] args) {
        try {
            String URLWebsite = args[0];
            Proxy proxy = new Proxy(URLWebsite);
            String webPageContent = proxy.setWebPageByProxy(URLWebsite);
            if(webPageContent != null) {
                WebsiteInfo contentSite = new WebsiteInfo(URLWebsite, webPageContent);
                System.out.println(contentSite.getWebPageContentHTML());
                // 
                System.setSecurityManager(new RMISecurityManager());
                try {
                    Registry registro = LocateRegistry.getRegistry("192.168.1.102", 1099);
                    HelloPerson stub = (RemoteS) registro.lookup("sObj");
                    stub.loadPage(contentSite);
                } catch (Exception e) {
                    System.err.println("Client exception: " + e.toString());
                }
            } else {
                System.err.println("The content of the web page couldn't been retrieved");
            }
        } catch(ArrayIndexOutOfBoundsException exc) {
            System.err.println("No parameter where passed");
        }
    }
}