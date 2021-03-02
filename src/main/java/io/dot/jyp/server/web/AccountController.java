package io.dot.jyp.server.web;

import io.dot.jyp.server.application.AccountApplicationService;
import io.dot.jyp.server.application.dto.AccountChangePassphraseRequest;
import io.dot.jyp.server.application.dto.AccountLoginRequest;
import io.dot.jyp.server.application.dto.AccountSignUpRequest;
import io.dot.jyp.server.application.dto.AccountVerifyPassphraseRequest;
import io.dot.jyp.server.config.UserAccount;
import io.dot.jyp.server.domain.Account;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class AccountController {

    private final AccountApplicationService accountApplicationService;

    public AccountController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void signUp(@RequestBody final AccountSignUpRequest request) {
        accountApplicationService.signUp(request);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public void login(@RequestBody final AccountLoginRequest request) {
        accountApplicationService.login(request);
    }

    @PatchMapping("/{accountId}/passphrase")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("#account.id == #accountId")
    public void changePassphrase(
            @Parameter(hidden = true) @UserAccount final Account account,
            @PathVariable final String accountId,
            @RequestBody final AccountChangePassphraseRequest request
    ) {
        this.accountApplicationService.changePassphrase(account, request);
    }

    @PostMapping("/{accountId}/passphrase/verify")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("#account.id == #accountId")
    public void verifyPassphrase(
            @Parameter(hidden = true) @UserAccount final Account account,
            @PathVariable final String accountId,
            @RequestBody final AccountVerifyPassphraseRequest request
    ) {
        this.accountApplicationService.verifyPassphrase(accountId, request.getPassphrase());
    }
}
