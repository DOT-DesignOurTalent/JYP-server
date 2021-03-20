package io.dot.jyp.server.infra.message;

import io.dot.jyp.server.domain.Group;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

  private RabbitTemplate rabbitTemplate;

  @Autowired
  EventDispatcher(final RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void joinMessage(final Group group) {
    rabbitTemplate.convertAndSend(
        "exchange",
        "queue.group",
        group
    );
  }

  public void sendMessage(final Group group) {
    rabbitTemplate.convertAndSend(
        "exchange",
        "queue.group",
        group
    );
  }
}
