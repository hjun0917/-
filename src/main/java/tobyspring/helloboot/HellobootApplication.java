package tobyspring.helloboot;

import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;
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
            // Servlet 의 이름을 짓고, Servlet 생성
            servletContext.addServlet("hello", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    /*
                    요청 파라미터에서 정보를 전달받아 동적인 응답을 만들 수 있음
                    (Spring boot 의 @RequestParam 혹은 쿼리스트링을 받는 것과 동일)
                     */
                    String name = req.getParameter("name");

                    /*
                    setStatus 와 setHeader 의 정보를 하드코딩하게 되면 휴먼 에러(오타 등)의 이유로
                    올바른 응답값을 클라이언트가 받지 못할 수 있다.
                    Spring Framework 에서 Enum 을 통해 제공하는 값들을 사용하자.
                     */
                    resp.setStatus(HttpStatus.OK.value());
                    resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                    resp.getWriter().println("Hello "+name);
                }
                // Servlet 컨테이너가 올바른 Servlet 으로 요청을 위임할 수 있도록 Mapping 해주는 과정
            }).addMapping("/hello");
        });
        webServer.start();
    }
}
