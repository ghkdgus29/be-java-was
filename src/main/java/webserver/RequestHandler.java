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

            DispatcherServlet.service(httpRequest, httpResponse);

            sendResponseMessage(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void sendResponseMessage(OutputStream out, HttpResponse httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(httpResponse.toBytes());
        dos.write(httpResponse.getMessageBody());
    }
}
