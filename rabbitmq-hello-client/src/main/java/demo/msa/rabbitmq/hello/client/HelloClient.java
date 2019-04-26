package demo.msa.rabbitmq.hello.client;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author unisk1123
 * @Description
 * @create 2019/4/25
 */
@Component
public class HelloClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend("hello-queue", message);
    }

    public void sendFoo(Foo foo) {
        rabbitTemplate.convertAndSend("foo-queue", foo);
    }
}
