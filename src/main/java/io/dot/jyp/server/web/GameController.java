//package io.dot.jyp.server.web;
//
//import io.dot.jyp.server.application.GameApplicationService;
//import io.dot.jyp.server.application.dto.GameStartRequest;
//import io.dot.jyp.server.application.dto.GameStartResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/v1/game")
//public class GameController {
//    private final GameApplicationService gameApplicationService;
//
//    public GameController(GameApplicationService gameApplicationService) {
//        this.gameApplicationService = gameApplicationService;
//    }
//
//    @PostMapping("/start")
//    @ResponseStatus(value = HttpStatus.OK)
//    public GameStartResponse gameStart(@RequestBody final GameStartRequest request) {
//        return GameStartResponse.of("임시 이름", "중식");
//    }
//}
