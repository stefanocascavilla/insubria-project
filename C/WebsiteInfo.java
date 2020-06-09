import java.io.Serializable;

/**
* Classe contenente l'URL e il contenuto in HTML di un determinato sito
*/
public class WebsiteInfo implements Serializable {
    // id per la serializzazione
    private static final long serialVersionUID = 1L;
    // URL del sito
    private String url;
    // contenuto HTML della pagina WEB ottenuta dal costruttore
    private String webPageContentHTML;

    /**
    * Costruttore della classe WebsiteInfo
    *
    * @param url URL di un sito WEB
    * @param webPageContentHTML contenuto HTML della pagina WEB avente come URL il campo url
    */
    public WebsiteInfo(String url, String webPageContentHTML) {
        this.url = url;
        this.webPageContentHTML = webPageContentHTML;
    }

    /**
    * Returns the value of the url field
    */
    public String getUrl() { return this.url; }

    /**
    * Returns the value of the webPageContentHTML field
    */
    public String getWebPageContentHTML() { return this.webPageContentHTML; }