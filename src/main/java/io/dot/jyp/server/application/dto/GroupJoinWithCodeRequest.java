package io.dot.jyp.server.application.dto;

import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.Menu;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupJoinWithCodeRequest {

  private Account account;
  private String code;
  private List<Menu> menus;
}
