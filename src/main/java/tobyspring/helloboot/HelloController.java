package tobyspring.helloboot;


import java.util.Objects;

public class HelloController {
    /*
    인터페이스를 필드에 변수로 생성
    -> 모든 구현 클래스로 초기화 될 수 있음.
    기존 코드로 SimpleHelloService 에 강하게 의존하고 있었지만
    아래와 같은 코드로 변경을 통해 의존성을 감소(?)시킴
     */
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name) {

        // Object 타입으로 인자를 체크(?)
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
