package io.dot.jyp.server.web;

import io.dot.jyp.server.application.GameApplicationService;
import io.dot.jyp.server.application.dto.GameAppealRequest;
import io.dot.jyp.server.application.dto.GameApproveAppealRequest;
import io.dot.jyp.server.application.dto.GameDeclineAppealRequest;
import io.dot.jyp.server.application.dto.GameReadyRequest;
import io.dot.jyp.server.application.dto.GameStartRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/game")
public class GameController {

  private final GameApplicationService gameApplicationService;

  public GameController(GameApplicationService gameApplicationService) {
    this.gameApplicationService = gameApplicationService;
  }

  @PostMapping("/ready")
  @ResponseStatus(value = HttpStatus.OK)
  public void ready(@RequestBody final GameReadyRequest request) {
  }

  @PostMapping("/start")
  @ResponseStatus(value = HttpStatus.OK)
  public void start(@RequestBody final GameStartRequest request) {
  }

  @PostMapping("/appeal")
  @ResponseStatus(value = HttpStatus.OK)
  public void appeal(@RequestBody final GameAppealRequest request) {
  }

  @PostMapping("/appeal/approve")
  @ResponseStatus(value = HttpStatus.OK)
  public void approveAppeal(@RequestBody final GameApproveAppealRequest request) {
  }

  @PostMapping("/appeal/decline")
  @ResponseStatus(value = HttpStatus.OK)
  public void declineAppeal(@RequestBody final GameDeclineAppealRequest request) {
  }
}
