package webserver;

import db.Database;
import model.RequestType;
import model.User;
import util.StatusCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {

    private static final String HTTP_VERSION = "HTTP/1.1";

    private static final String LOGIN_ELEMENT = "로그인";
    private static final String USERS_LIST_ELEMENT = "<tbody class=\"user-list\">";


    private StatusCode statusCode = StatusCode.OK;
    private Map<String, String> headers = new HashMap<>();
    private byte[] messageBody = {};
    private String redirectUrl;

    private RequestType requestType;

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isRedirect() {
        return statusCode.isRedirect();
    }

    public void setCookie(String cookieName, String cookieValue) {
        addHeader("Set-Cookie", cookieName + "=" + cookieValue + "; Path=/");
    }

    public void setContent(String viewName, User sessionUser) throws IOException {
        if (isRedirect()) {
            addHeader("Location", redirectUrl);
            return;
        }

        this.messageBody = getFile(viewName, sessionUser);

        addHeader("Content-Type", requestType.getContentType());
        addHeader("Content-Length", String.valueOf(messageBody.length));
    }

    public void setRequestType(String viewName) {
        this.requestType = RequestType.of(viewName);
    }

    public byte[] toBytes() {
        StringBuilder sb = new StringBuilder();

        sb.append(HTTP_VERSION + " " + statusCode.getStatusValue() + " " + statusCode + " \r\n");

        headers.forEach((k, v) -> {
            sb.append(k + ": " + v + "\r\n");
        });
        sb.append("\r\n");

        byte[] headers = sb.toString().getBytes();
        byte[] messageBody = this.messageBody;

        return concatHeadersAndBody(headers, messageBody);
    }

    private byte[] concatHeadersAndBody(byte[] headers, byte[] messageBody) {
        byte[] result = new byte[headers.length + messageBody.length];
        System.arraycopy(headers, 0, result, 0, headers.length);
        System.arraycopy(messageBody, 0, result, headers.length, messageBody.length);
        return result;
    }

    /**
     * HTML 인 경우, 한 줄씩 읽기 위해 BufferedReader, FileReader 를 사용해서 파일 정보를 불러온다.
     * (동적 HTML을 작성하기 위해)
     * HTML 이 아닌 경우, 문자열이 아닐 수 있고, 한 줄씩 읽어올 필요도 없으므로 readAllBytes 를 사용한다.
     *
     * @param viewName
     * @return
     */
    private byte[] getFile(String viewName, User sessionUser) throws IOException {
        if (requestType == RequestType.HTML) {
            try (FileReader fr = new FileReader(requestType.getAbsolutePath(viewName));
                 BufferedReader br = new BufferedReader(fr)) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    line = generateDynamicHTML(sessionUser, line);
                    sb.append(line + "\r\n");
                }
                return sb.toString().getBytes();
            }
        }

        return Files.readAllBytes(new File(requestType.getAbsolutePath(viewName)).toPath());
    }

    /**
     * HTML 에서 현재 읽어온 부분이 LOGIN_ELEMENT 이고, 현재 로그인을 성공해 세션이 존재하는 경우
     * LOGIN_ELEMENT 를 이름으로 변경한다.
     *
     * HTML 에서 현재 읽어온 부분이 USERS_LIST_ELEMENT 이면,
     * 동적으로 유저리스트를 생성해 반환한다.
     *
     * @param sessionUser
     * @param line
     * @return
     */
    private static String generateDynamicHTML(User sessionUser, String line) {
        if (line.contains(LOGIN_ELEMENT) && (sessionUser != null)) {
            String userId = sessionUser.getUserId();
            line = "\t\t\t\t<li><a href=\"#\" style=\"font-weight: 900\">" + userId + "</a></li>\r\n";
        }

        StringBuilder sb = new StringBuilder();
        if (line.contains(USERS_LIST_ELEMENT)) {
            sb.append(line);

            List<User> users = Database.findAll();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                sb.append("\t\t\t\t<tr>\r\n");
                sb.append("\t\t\t\t\t<th scope=\"row\">" + (i + 1) + "</th> <td>" +user.getUserId() + "</td> <td>" + user.getName() + "</td> <td>" + user.getEmail() + "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\r\n");
                sb.append("\t\t\t\t</tr>\r\n");
            }
            line = sb.toString();
        }

        return line;
    }
}
