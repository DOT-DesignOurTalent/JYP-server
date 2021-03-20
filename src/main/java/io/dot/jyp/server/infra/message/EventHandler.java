package io.dot.jyp.server.infra.message;

import io.dot.jyp.server.application.GroupApplicationService;
import io.dot.jyp.server.domain.Group;
import io.dot.jyp.server.web.GroupController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class EventHandler {

  private GroupApplicationService groupApplicationService;
  private GroupController groupController;

  public EventHandler(
      final GroupApplicationService groupApplicationService,
      final GroupController groupController
  ) {
    this.groupApplicationService = groupApplicationService;
    this.groupController = groupController;
  }

  /**
   * Queue 로 넘어온 데이터를 Listen 합니다. 그 후 controller 의 postDepositMinedTx 매서드에 그 값을 전달합니다.
   *
   * @param group
   */
  @RabbitListener(queues = "groupQueue")
  public void receivedGroupMessage(final Group group) {
    try {
      groupController.send(group);
    } catch (final Exception e) {
      log.error("Error ", e);
      throw new AmqpRejectAndDontRequeueException(e);
    }
  }
}
