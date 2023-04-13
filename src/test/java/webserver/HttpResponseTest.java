package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    @Test
    @DisplayName("HttpResponse 객체는 statusCode, header 를 바탕으로 응답헤더를 bytes로 만들어 반환한다.")
    void addHeader() {
        HttpResponse httpResponse = new HttpResponse();

        httpResponse.setStatusCode(200);
        httpResponse.addHeader("Name", "Hyun");

        String sb = "HTTP/1.1 200 OK \r\n" +
                "Name: Hyun\r\n" +
                "\r\n";

        byte[] expected = sb.getBytes();

        assertEquals(Arrays.toString(expected), Arrays.toString(httpResponse.toBytes()));
    }
}