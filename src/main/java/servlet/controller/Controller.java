package servlet.controller;

import java.util.Map;

public interface Controller {

    public String process(Map<String, String> parameters);
}
