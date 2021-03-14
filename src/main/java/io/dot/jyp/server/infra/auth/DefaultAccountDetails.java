package io.dot.jyp.server.infra.auth;

import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.Role;
import io.dot.jyp.server.domain.exception.AuthenticationException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class DefaultAccountDetails implements UserDetails {

  private final Account account;
  private final List<GrantedAuthority> grantedAuthorities;

  public static DefaultAccountDetails of(Account account) {
    return new DefaultAccountDetails(
        account,
        account.getRoles()
            .stream()
            .map(Role::getGrantedAuthorities)
            .flatMap(List::stream)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList())
    );
  }

  public static DefaultAccountDetails fromAuthentication(Authentication authentication) {
    if (authentication == null) {
      throw new AuthenticationException("authentication error");
    }
    if (!(authentication.getPrincipal() instanceof DefaultAccountDetails)) {
      throw new AuthenticationException("authentication error");
    }
    return (DefaultAccountDetails) authentication.getPrincipal();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return this.account.getPassphrase();
  }

  @Override
  public String getUsername() {
    return this.account.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}