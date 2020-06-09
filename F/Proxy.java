import java.net.*;
import java.io.*;

public class Proxy implements ProxyInterface {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Proxy(String url) {
        try {
            URI uri = new URI(url);
            InetAddress addr = InetAddress.getByName(uri.getHost());
            socket = new Socket(addr, ProxyInterface.PORT);
            // crea gli stream di input/output
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

    public String setWebPageByProxy(String url) {
        String firstLineHTMLPage = "";

        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getRawPath();
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
}