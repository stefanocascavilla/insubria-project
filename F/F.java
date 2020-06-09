public class F {
    public static void main(String[] args) {
        try {
            String URLWebsite = args[0];
            Proxy proxy = new Proxy(URLWebsite);
            String webPageContent = proxy.setWebPageByProxy(URLWebsite);
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