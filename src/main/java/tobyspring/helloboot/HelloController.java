package tobyspring.helloboot;


import java.util.Objects;

public class HelloController {
    public String hello(String name) {
        SimpleHelloService helloService = new SimpleHelloService();

        // Object 타입으로 인자를 체크(?)
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
