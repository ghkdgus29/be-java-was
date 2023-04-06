package webserver;

import model.User;
import model.StartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            String line = br.readLine();
            StartLine startLine = HttpRequest.getStartLine(line);

            if (startLine.getMethod().equals("GET") && startLine.getParamMap() != null) {         // 만약 GET 메서드 요청이 파라미터를 갖고 있다면, User 클래스 생성
                User user = new User(startLine.getParamMap());
                logger.debug("{}", user);
            }

            while (!line.equals("")) {
                logger.debug(line);
                line = br.readLine();
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(startLine.getPath()).toPath());

            HttpResponse.sendResponse200(dos, body, startLine);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
