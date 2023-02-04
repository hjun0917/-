package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

    public static void main(String[] args) {
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        // Servlet 컨테이너를 만드는 생성 함수
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            HelloController helloController = new HelloController();
            // Servlet 의 이름을 짓고, Servlet 생성
            servletContext.addServlet("frontController", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // Servlet 은 frontController 의 역할을 하고 서블릿 내부에서 아래와 같은 로직으로 요청을 처리
                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        /*
                        바인딩 : 요청 파라미터로 넘어온 값을 자바에서 사용하는 타입의 값으로 묶어(?)주는 것 -> 23/02/05 표현이 올바른지 모르겠음
                        자바 메서드를 호출할 때 사용될 인자 값으로 사용
                         */
                        String name = req.getParameter("name");

                        String ret = helloController.hello(name);

                        resp.setStatus(HttpStatus.OK.value());
                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                        resp.getWriter().println(ret);
                    } else if (req.getRequestURI().equals("/user")) {
                        //
                    } else {
                        // 모든 조건에 부합하지 않는 요청은 404 반환을 하기  위해
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }

                }
                // frontController 는 중앙화된 처리를 하기 위해 모든 요청을 다 받아야 한다.
            }).addMapping("/*");
        });
        webServer.start();
    }
}
