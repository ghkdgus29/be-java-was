package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    @Test
    @DisplayName("서버가 정상요청의 경우, Response 200 응답을 제대로 보낸다")
    void sendResponse200() {
        byte[] body = "hiiiiiiiiiiiiiii\n".getBytes();

        HttpResponse.sendResponse200(new DataOutputStream(System.out), body);
    }
}
