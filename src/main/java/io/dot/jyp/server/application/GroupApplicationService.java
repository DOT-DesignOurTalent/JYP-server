package io.dot.jyp.server.application;

import io.dot.jyp.server.application.dto.GroupCreateRequest;
import io.dot.jyp.server.application.dto.GroupCreateResponse;
import io.dot.jyp.server.domain.*;
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

    public GroupApplicationService(
            GroupRepository groupRepository,
            RoleRepository roleRepository,
            AccountRepository accountRepository,
            @Qualifier("OpenCsvClient") FileIoClient fileIoClient,
            @Qualifier("nicknamePath") String nicknamePath
    ) {
        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.fileIoClient = fileIoClient;
        this.nicknamePath = nicknamePath;
    }

    @Transactional
    public GroupCreateResponse createGroup(Account account, GroupCreateRequest request) {
        return null;
    }

    public String generateNickname() {
        return generateRandomNickname();
    }

    public String generateRandomNickname() {
        return fileIoClient.readCsvFile(nicknamePath).makeNickname();
    }
}
