package demo.msa.activemq.hello.client;

import org.apache.activemq.util.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void init() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.restart();
        for (int i = 0; i < 1000; i++) {
            helloClient.send("hello world");
        }
        stopWatch.stop();
        System.out.println("time: " + stopWatch.taken());
    }

    @RequestMapping("/send/{message}")
    public String send(@PathVariable("message") String message) {
        helloClient.send(message);
        return message;
    }
    public static void main(String[] args) {
        SpringApplication.run(HelloClientApplication.class, args);
    }
}
