package demo.msa.rabbitmq.hello.client;

import org.springframework.amqp.core.DirectExchange;
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

    @Autowired
    private DirectExchange exchange;

    public String sendRpc(String message) {
        return (String) rabbitTemplate.convertSendAndReceive(exchange.getName(), "rpc", message);
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend("hello-queue", message);
    }

    public void sendFoo(Foo foo) {
        rabbitTemplate.convertAndSend("foo-queue", foo);
    }
}
