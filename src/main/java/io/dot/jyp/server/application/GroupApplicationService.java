package io.dot.jyp.server.application;

import io.dot.jyp.server.application.dto.*;
import io.dot.jyp.server.domain.*;
import io.dot.jyp.server.domain.exception.BadRequestException;
import io.dot.jyp.server.domain.exception.ErrorCode;
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
                request.getDiners(),
                randomValueGenerator.generateRandomCode(),
                nickname
        );
        groupRepository.save(group);

        return GroupCreateResponse.of(
                group.getCode(),
                nickname
        );
    }

    @Transactional
    public GroupEnterWithCodeResponse enterWithCode(GroupEnterWithCodeRequest request) {
        String nickname = fileIoClient.readCsvFile(nicknamePath).generateRandomText();

        Group group = groupRepository.findGroupByCode(request.getCode())
                .orElseThrow(() -> new BadRequestException(String.format("group code '%s' does not exist", request.getCode()), ErrorCode.BAD_REQUEST));

        group.addNickname(nickname);
        groupRepository.save(group);

        return GroupEnterWithCodeResponse.of(nickname);
    }

    @Transactional
    public void groupAddDiners(GroupAddDinersRequest request, String code) {
        Group group = groupRepository.findGroupByCode(code)
                .orElseThrow(() -> new BadRequestException(String.format("group code '%s' does not exist", code), ErrorCode.BAD_REQUEST));

        group.addDiners(request.getDiners());
        groupRepository.save(group);
    }

}
