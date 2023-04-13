package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.DispatcherServlet;
import util.RequestSeparater;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            HttpRequest httpRequest = RequestSeparater.askHttpRequest(br);
            HttpResponse httpResponse = new HttpResponse();

            String viewName = DispatcherServlet.service(httpRequest, httpResponse);             // 컨트롤러가 반환한 viewName

            if (httpResponse.isRedirect()) {                                                    // 리다이렉트 응답 시
                httpResponse.addHeader("Location", viewName);
                sendResponseMessage(out, httpResponse);
                return;
            }

            String absolutePath = resolveView(viewName, httpRequest);                           // 200 응답 시
            httpResponse.setContent(absolutePath, httpRequest);
            sendResponseMessage(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static String resolveView(String viewName, HttpRequest httpRequest) {
        return httpRequest.getAbsolutePath(viewName);
    }

    private static void sendResponseMessage(OutputStream out, HttpResponse httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(httpResponse.toBytes());

        if (!httpResponse.isRedirect()) {
            dos.write(httpResponse.getMessageBody());
        }
    }
}
