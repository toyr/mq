package demo.msa.rabbitmq.hello.server;

import demo.msa.rabbitmq.rpc.server.RpcReceiver;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author unisk1123
 * @Description
 * @create 2019/4/25
 */
@Component
public class HelloServer implements RpcReceiver<String, String> {

    /*@RabbitListener(queues = "hello-queue")
    public void receive(String message) {
        System.out.println(message);
    }

    @RabbitListener(queues = "foo-queue")
    public void receive(Foo o) {
        System.out.println(o);
    }

    @RabbitListener(queues = "rpc-queue")
    public String receiveRpc(String message) {
        return "hello" + message;
    }*/

    @Override
    @RabbitListener(queues = "rpc-queue")
    public String receive(String message) {
        return "hello " + message;
    }
}
