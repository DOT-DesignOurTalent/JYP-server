package io.dot.jyp.server.application;

import io.dot.jyp.server.application.dto.AccountChangePassphraseRequest;
import io.dot.jyp.server.application.dto.AccountLoginRequest;
import io.dot.jyp.server.application.dto.AccountSignUpRequest;
import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.AccountRepository;
import io.dot.jyp.server.domain.PassphraseEncoder;
import io.dot.jyp.server.domain.PassphraseVerifier;
import io.dot.jyp.server.domain.RoleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Qualifier("accountService")
@Service
public class AccountApplicationService {

  private final AccountRepository accountRepository;
  private final PassphraseEncoder passphraseEncoder;
  private final PassphraseVerifier passphraseVerifier;
  private final RoleRepository roleRepository;

  public AccountApplicationService(
      AccountRepository accountRepository,
      PassphraseEncoder passphraseEncoder,
      PassphraseVerifier passphraseVerifier,
      RoleRepository roleRepository
  ) {
    this.accountRepository = accountRepository;
    this.passphraseEncoder = passphraseEncoder;
    this.passphraseVerifier = passphraseVerifier;
    this.roleRepository = roleRepository;
  }

  @Transactional
  public void signUp(AccountSignUpRequest request) {
    this.accountRepository.existsByEmailThenThrow(request.getEmail());

    Account targetAccount = Account.signup(
        request.getEmail(),
        this.passphraseEncoder.encode(request.getPassphrase())
    );
    this.accountRepository.save(targetAccount);
  }

  @Transactional(readOnly = true)
  public void verifyEmail(String email) {
    this.accountRepository.existsByEmailThenThrow(email);
  }

  @Transactional
  public void login(AccountLoginRequest request) {
    Account account = accountRepository.findByEmailOrElseThrow(request.getEmail());
    this.passphraseVerifier.validate(account.getEmail(), request.getPassphrase());
  }

  @Transactional
  public void changePassphrase(Account account, AccountChangePassphraseRequest request) {
    account.updatePassphrase(this.passphraseEncoder.encode(request.getNewPassphrase()));
    this.accountRepository.save(account);
  }

  @Transactional(readOnly = true)
  public void verifyPassphrase(String email, String passphrase) {
    this.passphraseVerifier.validate(email, passphrase);
  }
}
