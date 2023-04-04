package webserver;

public class HttpRequestMessageParser {

    public static String parseUrl(String startLine) {
        String url = startLine.split(" ")[1];
        if (url.equals("/")) {
            url = "/index.html";
        }
        return url;
    }
}
