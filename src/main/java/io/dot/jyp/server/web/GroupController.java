package io.dot.jyp.server.web;

import io.dot.jyp.server.application.GroupApplicationService;
import io.dot.jyp.server.application.dto.*;
import io.dot.jyp.server.config.UserAccount;
import io.dot.jyp.server.domain.Account;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return groupApplicationService.groupCreate(request);
    }

    @PostMapping("/enter-group-code")
    @ResponseStatus(value = HttpStatus.OK)
    public GroupEnterWithCodeResponse groupEnterWithCode(@RequestBody final GroupEnterWithCodeRequest request) {
        return groupApplicationService.groupEnterWithCode(request);
    }

//    @PostMapping("/{groupCode}/add-diners")
//    @ResponseStatus(value = HttpStatus.OK)
//    public void groupEnterWithCode(
//            @RequestBody final GroupAddDinersRequest request,
//            @PathVariable final String groupCode
//    ) {
//        groupApplicationService.groupAddDiners(request, groupCode);
//    }

}