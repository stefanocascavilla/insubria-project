/**
* Interfacccia implementata dalla classe Proxy
*/
public interface ProxyInterface {
    // porta a cui connettersi per scaricare una pagina WEB
    public static final int PORT = 80;
    /**
    * Metodo che restituisce il contenuto HTML della pagina il cui URL è stato passato come parametro
    *
    * @param url URL della pagina WEB da scaricare
    */
    public String setWebPageByProxy(String url);
}