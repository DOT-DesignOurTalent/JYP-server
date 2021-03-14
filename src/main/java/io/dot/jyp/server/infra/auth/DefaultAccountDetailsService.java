package io.dot.jyp.server.infra.auth;

import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Qualifier("accountDetailsService")
public class DefaultAccountDetailsService implements UserDetailsService {

  private final AccountRepository accountRepository;

  public DefaultAccountDetailsService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public DefaultAccountDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = this.accountRepository
        .findWithRoleByEmailAndStatus(email, Account.Status.ACTIVE)
        .orElseThrow(() -> new UsernameNotFoundException(email));
    return DefaultAccountDetails.of(account);
  }
}