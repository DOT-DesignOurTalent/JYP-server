package io.dot.jyp.server.application;

import io.dot.jyp.server.application.dto.GameAppealRequest;
import io.dot.jyp.server.application.dto.GameDeclineAppealRequest;
import io.dot.jyp.server.application.dto.GameReadyRequest;
import io.dot.jyp.server.application.dto.GameStartRequest;
import io.dot.jyp.server.application.dto.GameStartResponse;
import io.dot.jyp.server.domain.AccountRepository;
import io.dot.jyp.server.domain.GroupRepository;
import io.dot.jyp.server.domain.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class GameApplicationService {

  private final AccountRepository accountRepository;
  private final GroupRepository groupRepository;
  private final RoleRepository roleRepository;

  public GameApplicationService(
      AccountRepository accountRepository,
      GroupRepository groupRepository,
      RoleRepository roleRepository
  ) {
    this.accountRepository = accountRepository;
    this.groupRepository = groupRepository;
    this.roleRepository = roleRepository;
  }


  @Transactional
  public void ready(GameReadyRequest request) {
  }

  @Transactional
  public GameStartResponse start(GameStartRequest request) {
    return null;
  }


  @Transactional
  public void appeal(GameAppealRequest request) {
  }

  @Transactional
  public void approveAppeal(GameDeclineAppealRequest reqest) {

  }

}
