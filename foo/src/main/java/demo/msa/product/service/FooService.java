package demo.msa.product.service;

import demo.msa.event.Event;
import demo.msa.event.EventManager;
import demo.msa.event.EventType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FooService {

  private JdbcTemplate jdbcTemplate;
  private EventManager eventManager;

  @Autowired
  public FooService(JdbcTemplate jdbcTemplate, EventManager eventManager) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventManager = eventManager;
  }

  @Transactional
  public void insertFoo(String name) {
    String fooId = UUID.randomUUID().toString();
    try {
      jdbcTemplate.update(
          "INSERT INTO foo (id, name) VALUES (?, ?)",
          fooId, name
      );
    } finally {
      // 创建一个Event对象
      Event event = new Event(EventType.CREATE, "Foo", fooId);
      // 将一个Event对象插入事件表中
      eventManager.insertEvent(event);
      // 将Event对象写入成功队列中
      eventManager.sendEventQueue("foo-success-queue", event);
    }
  }

  @Transactional
  @RabbitListener(queues = "bar-failure-queue")
  public void handleBarFailure(Event event) {
    String fooId = eventManager.queryModelId(event.getId());
    jdbcTemplate.update(
        "DELETE FROM foo WHERE id = ?",
        fooId
    );
  }
}
