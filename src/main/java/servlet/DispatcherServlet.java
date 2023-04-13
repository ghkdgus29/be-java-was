package servlet;

import servlet.controller.*;
import util.StatusCode;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {

    private static final int REDIRECT_URL_IDX = 1;
    private static final Map<String, Controller> controllerMap = addController();

    private static Map<String, Controller> addController() {
        HashMap<String, Controller> controllerMap = new HashMap<>();

        controllerMap.put("/", new IndexController());
        controllerMap.put("/index.html", new IndexController());
        controllerMap.put("/user/form.html", new UserFormController());
        controllerMap.put("/user/create", new UserSaveController());
        controllerMap.put("/user/login.html", new LoginFormController());
        controllerMap.put("/user/login", new LoginController());

        return controllerMap;
    }


    public static String service(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestUrl = httpRequest.getUrl();

        Controller controller = controllerMap.get(requestUrl);
        if (controller == null) {                                               // CSS, JS 와 같은 static 타입 요청이 올 경우
            return requestUrl;
        }

        String viewName = controller.process(httpRequest.getParameters());

        if (viewName.startsWith("redirect:")) {                                        // redirect 요청일 경우
            String redirectUrl = viewName.split(":")[REDIRECT_URL_IDX];
            httpResponse.setStatusCode(StatusCode.FOUND);
            httpResponse.setRedirectUrl(redirectUrl);

            return "redirect";
        }

        return viewName;                                                        // 뷰를 반환하는 정상 요청인 경우
    }
}
