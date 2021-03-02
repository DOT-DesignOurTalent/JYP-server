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


    public String generateRandomGroupCode(){ return Group.generateGroupCode(); }

    public String generateRandomNickname() {
        return fileIoClient.readCsvFile(nicknamePath).makeNickname();
    }


    @Transactional
    public GroupCreateResponse groupCreate(GroupCreateRequest request) {
        String groupCode = generateRandomGroupCode();
        groupRepository.existsByGroupCodeThenThrow(groupCode);
        String nickname = generateRandomNickname();
        Group group = Group.groupCreate(
                request.getDiners(),
                groupCode,
                nickname
        );
        groupRepository.save(group);

        return GroupCreateResponse.of(groupCode,nickname);
    }

    @Transactional
    public GroupEnterWithCodeResponse groupEnterWithCode(GroupEnterWithCodeRequest request) {
        String groupCode = request.getGroupCode();
        Group group = groupRepository.findGroupByGroupCode(groupCode)
                .orElseThrow(() -> new BadRequestException(String.format("group code '%s' does not exist", groupCode), ErrorCode.BAD_REQUEST));

        String nickname = generateRandomNickname();
        group.addNickname(nickname);
        groupRepository.save(group);

        return GroupEnterWithCodeResponse.of(nickname);
    }
    @Transactional
    public void groupAddDiners(GroupAddDinersRequest request, String groupCode) {
        Group group = groupRepository.findGroupByGroupCode(groupCode)
                .orElseThrow(() -> new BadRequestException(String.format("group code '%s' does not exist", groupCode), ErrorCode.BAD_REQUEST));

        group.addDiners(request.getDiners());
        groupRepository.save(group);
    }

}
