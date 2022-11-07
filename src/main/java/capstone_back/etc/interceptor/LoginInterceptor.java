package capstone_back.etc.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginInterceptor Init");

        String token = request.getHeader("AUTHORIZATION");
        if(token == null) {
            String errorJson = objectMapper.writeValueAsString(new Response("fail", "token required"));
            response.getWriter().write(errorJson);
            return false;
        }

        return true;
    }
    public class Response {
        String result;
        Object data;

        public Response(String result, Object data) {
            this.result = result;
            this.data = data;
        }
    }
}
