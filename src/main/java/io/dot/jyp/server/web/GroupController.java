package io.dot.jyp.server.web;

import io.dot.jyp.server.application.GroupApplicationService;
import io.dot.jyp.server.application.dto.GroupCreateRequest;
import io.dot.jyp.server.application.dto.GroupCreateResponse;
import io.dot.jyp.server.application.dto.GroupJoinWithCodeRequest;
import io.dot.jyp.server.application.dto.GroupJoinWithCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

  private final GroupApplicationService groupApplicationService;

  public GroupController(GroupApplicationService groupApplicationSService) {
    this.groupApplicationService = groupApplicationSService;
  }

  @PostMapping("/create")
  @ResponseStatus(value = HttpStatus.CREATED)
  public GroupCreateResponse create(@RequestBody final GroupCreateRequest request) {
    return groupApplicationService.create(request);
  }

  @PostMapping("/join")
  @ResponseStatus(value = HttpStatus.OK)
  public GroupJoinWithCodeResponse enterWithCode(
      @RequestBody final GroupJoinWithCodeRequest request) {
    return groupApplicationService.joinWithCode(request);
  }
}