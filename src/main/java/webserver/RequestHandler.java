package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

public class RequestHandler implements Runnable {

    private static final String PATH = "src/main/resources/templates";
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

            String method = HttpRequest.parseMethod(line);                     // HttpRequestMessageParser 클래스를 활용해 method, url, parameter를 파싱
            String url = HttpRequest.parseUrl(line);
            Map<String, String> paramMap = HttpRequest.parseParams(line);

            if (method.equals("GET") && paramMap != null) {         // 만약 GET 메서드 요청이 파라미터를 갖고 있다면, User 클래스 생성
                User user = new User(paramMap);
                logger.debug("{}", user);
            }

            while (!line.equals("")) {
                logger.debug(line);
                line = br.readLine();
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(PATH + url).toPath());

            HttpResponse.sendResponse200(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
