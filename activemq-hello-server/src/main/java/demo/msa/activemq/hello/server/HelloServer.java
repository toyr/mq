package demo.msa.activemq.hello.server;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author unisk1123
 * @Description
 * @create 2019/4/25
 */
@Component
public class HelloServer {

    @JmsListener(destination = "hello-queue")
    public void receive(String message) {
        System.out.println(message);
    }
}
