import java.net.*;
import java.io.*;

public class F {
    public static String setWebPageByProxy(String url) {
        URI uri;
        String host = "";
        String path = "";
        String firstLineHTMLPage = "";
        Socket socket = null;

        try {
            uri = new URI(url);
            host = uri.getHost();
            path = uri.getRawPath();
            socket = new Socket(host, 80);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            // Richiesta oggetto
            writer.print(
                "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n\r\n"
            );
            writer.flush();
            // Risposta

            String line;
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
            System.out.println(firstLineHTMLPage);
            System.out.println("*** closing...");
            try {
                if(socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch(IOException exc) {
                System.err.println("ERROR error while closing socket");
            }
            return firstLineHTMLPage;
        }
    }

    public static void main(String[] args) {
        try {
            String URLWebsite = args[0];
            String webPageContent = F.setWebPageByProxy(URLWebsite);
            if(webPageContent != null) {
                WebsiteInfo contentSite = new WebsiteInfo(URLWebsite, webPageContent);
                System.out.println(contentSite.getWebPageContentHTML());
                // loadPage()
            } else {
                System.err.println("The content of the web page couldn't been retrieved");
            }
        } catch(ArrayIndexOutOfBoundsException exc) {
            System.err.println("No parameter where passed");
        }
    }
}