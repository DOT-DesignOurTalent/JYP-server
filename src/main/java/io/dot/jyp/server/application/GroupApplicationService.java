package io.dot.jyp.server.application;

import io.dot.jyp.server.application.dto.GroupCreateRequest;
import io.dot.jyp.server.application.dto.GroupCreateResponse;
import io.dot.jyp.server.application.dto.GroupJoinWithCodeRequest;
import io.dot.jyp.server.application.dto.GroupJoinWithCodeResponse;
import io.dot.jyp.server.domain.AccountRepository;
import io.dot.jyp.server.domain.FileIoClient;
import io.dot.jyp.server.domain.Group;
import io.dot.jyp.server.domain.GroupMessage;
import io.dot.jyp.server.domain.GroupMessage.MessageType;
import io.dot.jyp.server.domain.GroupRepository;
import io.dot.jyp.server.domain.Menu;
import io.dot.jyp.server.domain.RandomValueGenerator;
import io.dot.jyp.server.domain.RoleRepository;
import io.dot.jyp.server.web.GroupController;
import io.dot.jyp.server.web.MessageController;
import java.util.stream.Collectors;
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

    Group group = Group.create(
        request.getMenus(),
        randomValueGenerator.generateRandomCode()
    );

    String menus=request.getMenus().stream()
        .map(s->s.getName())
        .collect(Collectors.joining(","));
    for(Menu i : request.getMenus()){
      menus +=i.getName();
    }

    GroupMessage groupMessage = new GroupMessage(
        MessageType.ENTER,
        group.getCode(),
        nickname,
        1,
        menus);
    groupRepository.save(group);

    return GroupCreateResponse.of(
        group.getCode(),
        nickname
    );
  }

  @Transactional
  public GroupJoinWithCodeResponse joinWithCode(GroupJoinWithCodeRequest request) {
    String nickname = fileIoClient.readCsvFile(nicknamePath).generateRandomText();

    Group group = groupRepository.findGroupByCodeOrElseThrow(request.getCode());

    String menus=request.getMenus().stream()
        .map(s->s.getName())
        .collect(Collectors.joining(","));
    for(Menu i : request.getMenus()){
      menus +=i.getName();
    }

    GroupMessage groupMessage = new GroupMessage(
        MessageType.ENTER,
        group.getCode(),
        nickname,
        2,
        menus);

    group.addMenu(request.getMenus());
    groupRepository.save(group);

    return GroupJoinWithCodeResponse.of(nickname);
  }
}
