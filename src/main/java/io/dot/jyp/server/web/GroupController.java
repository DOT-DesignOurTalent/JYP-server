package io.dot.jyp.server.web;

import io.dot.jyp.server.application.GroupApplicationService;
import io.dot.jyp.server.application.dto.GroupCreateRequest;
import io.dot.jyp.server.application.dto.GroupCreateResponse;
import io.dot.jyp.server.application.dto.GroupEnterWithCodeRequest;
import io.dot.jyp.server.application.dto.GroupEnterWithCodeResponse;
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
        return groupApplicationService.create(request);
    }

    @PostMapping("/enter-group-code")
    @ResponseStatus(value = HttpStatus.OK)
    public GroupEnterWithCodeResponse enterWithCode(@RequestBody final GroupEnterWithCodeRequest request) {
        return groupApplicationService.enterWithCode(request);
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