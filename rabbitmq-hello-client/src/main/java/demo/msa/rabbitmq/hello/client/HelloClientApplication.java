package demo.msa.rabbitmq.hello.client;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author unisk1123
 * @Description
 * @create 2019/4/25
 */
@RestController
@SpringBootApplication
public class HelloClientApplication {

    @Autowired
    private HelloClient helloClient;

    @Bean
    public Queue helloQueue() {
        return new Queue("hello-queue");
    }

    @Bean
    public Queue fooQueue() {
        return new Queue("foo-queue");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @PostConstruct
    public void init() {
//        helloClient.send("hello world");
//        Foo foo = new Foo();
//        foo.setAge(10);
//        foo.setName("foo");
//        helloClient.sendFoo(foo);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
//        for (int i = 0; i < 1000; i++) {
//            helloClient.send("hello workd");
//        }

        int threads = 10;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            final CountDownLatch begin = new CountDownLatch(1);
            final CountDownLatch end = new CountDownLatch(threads);
            for (int n = 0; n < threads; n++) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            begin.await();
                            for (int i = 0; i < 1000; i++) {
                                helloClient.send("hello world");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            end.countDown();
                        }
                    }
                });
            }
            begin.countDown();
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }

        stopWatch.stop();
        System.out.println("time: " + stopWatch.getTotalTimeSeconds());
    }

    @GetMapping("/send/{message}")
    public String send(@PathVariable("message") String message) {
        helloClient.send(message);
        return message;
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloClientApplication.class, args);
    }
}
