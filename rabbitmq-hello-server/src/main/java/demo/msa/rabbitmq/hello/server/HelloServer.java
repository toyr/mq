package demo.msa.rabbitmq.hello.server;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author unisk1123
 * @Description
 * @create 2019/4/25
 */
@Component
public class HelloServer {

    @RabbitListener(queues = "hello-queue")
    public void receive(String message) {
        System.out.println(message);
    }

    @RabbitListener(queues = "foo-queue")
    public void receive(Foo o) {
        System.out.println(o);
    }
}
