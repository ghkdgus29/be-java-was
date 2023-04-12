package webserver;

import model.StartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestSeparater;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

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

            RequestSeparater requestSeparater = new RequestSeparater(br);
            HttpRequest httpRequest = requestSeparater.askHttpRequest();


            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(httpRequest.getPath()).toPath());

            HttpResponse.sendResponse200(dos, body, httpRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
