package LearnMate.dev.utils;

import LearnMate.dev.common.status.ErrorStatus;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ExceptionHandlerUtil {

    public static void exceptionHandler(HttpServletResponse response, ErrorStatus errorStatus,
                                        int statusCode) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);
        JSONObject responseJson = new JSONObject();

        try {
            responseJson.put("isSuccess", "false");
            responseJson.put("message", errorStatus.getMessage());
            responseJson.put("code", errorStatus.getCode());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().print(responseJson);

    }

}
