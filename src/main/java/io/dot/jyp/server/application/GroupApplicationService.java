package io.dot.jyp.server.application;

import io.dot.jyp.server.application.dto.GroupCreateRequest;
import io.dot.jyp.server.application.dto.GroupCreateResponse;
import io.dot.jyp.server.application.dto.GroupJoinWithCodeRequest;
import io.dot.jyp.server.application.dto.GroupJoinWithCodeResponse;
import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.AccountRepository;
import io.dot.jyp.server.domain.FileIoClient;
import io.dot.jyp.server.domain.Group;
import io.dot.jyp.server.domain.GroupRepository;
import io.dot.jyp.server.domain.RandomValueGenerator;
import io.dot.jyp.server.domain.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class GroupApplicationService {

  private final GroupRepository groupRepository;
  private final RoleRepository roleRepository;
  private final AccountRepository accountRepository;
  private final FileIoClient fileIoClient;
  private final String nicknamePath;
  private final RandomValueGenerator randomValueGenerator;

  public GroupApplicationService(
      GroupRepository groupRepository,
      RoleRepository roleRepository,
      AccountRepository accountRepository,
      @Qualifier("OpenCsvClient") FileIoClient fileIoClient,
      @Qualifier("nicknamePath") String nicknamePath,
      RandomValueGenerator randomValueGenerator
  ) {
    this.groupRepository = groupRepository;
    this.roleRepository = roleRepository;
    this.accountRepository = accountRepository;
    this.fileIoClient = fileIoClient;
    this.nicknamePath = nicknamePath;
    this.randomValueGenerator = randomValueGenerator;
  }

  @Transactional
  public GroupCreateResponse create(GroupCreateRequest request) {
    String nickname = fileIoClient.readCsvFile(nicknamePath).generateRandomText();

    Account account = accountRepository.findWithRoleByEmailAndStatusOrElseThrow(
        request.getAccount().getEmail(),
        request.getAccount().getStatus()
    );
    Group group = Group.create(
        request.getMenus(),
        randomValueGenerator.generateRandomCode()
    );

    account.addNickname(nickname);
    account.assignHostRole();
    groupRepository.save(group);
    accountRepository.save(account);

    return GroupCreateResponse.of(
        group.getCode(),
        nickname
    );
  }

  @Transactional
  public GroupJoinWithCodeResponse joinWithCode(GroupJoinWithCodeRequest request) {
    String nickname = fileIoClient.readCsvFile(nicknamePath).generateRandomText();
    Account account = accountRepository.findWithRoleByEmailAndStatusOrElseThrow(
        request.getAccount().getEmail(),
        request.getAccount().getStatus()
    );
    Group group = groupRepository.findGroupByCodeOrElseThrow(request.getCode());

    account.addNickname(nickname);
    group.addMenu(request.getMenus());
    groupRepository.save(group);
    accountRepository.save(account);

    return GroupJoinWithCodeResponse.of(nickname);
  }
}
