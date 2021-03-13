package io.dot.jyp.server.web;

import io.dot.jyp.server.application.AccountApplicationService;
import io.dot.jyp.server.application.dto.AccountChangePassphraseRequest;
import io.dot.jyp.server.application.dto.AccountLoginRequest;
import io.dot.jyp.server.application.dto.AccountSignUpRequest;
import io.dot.jyp.server.application.dto.AccountVerifyEmailRequest;
import io.dot.jyp.server.application.dto.AccountVerifyPassphraseRequest;
import io.dot.jyp.server.config.UserAccount;
import io.dot.jyp.server.domain.Account;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    this.accountApplicationService.signUp(request);
  }

  @PostMapping("/signup/email/verify")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void verifyEmail(@RequestBody final AccountVerifyEmailRequest request) {
    this.accountApplicationService.verifyEmail(request.getEmail());
  }

  @PostMapping("/login")
  @ResponseStatus(value = HttpStatus.OK)
  public void login(@RequestBody final AccountLoginRequest request) {
    this.accountApplicationService.login(request);
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
