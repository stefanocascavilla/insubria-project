package C;

import java.io.Serializable;

public class WebsiteInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String webPageContentHTML;

    public WebsiteInfo(String url, String webPageContentHTML) {
        this.url = url;
        this.webPageContentHTML = webPageContentHTML;
    }

    public String getUrl() { return this.url; }

    public String getWebPageContentHTML() { return this.webPageContentHTML; }
}