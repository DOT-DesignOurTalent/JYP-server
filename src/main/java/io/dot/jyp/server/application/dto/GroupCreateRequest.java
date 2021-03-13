package io.dot.jyp.server.application.dto;

import io.dot.jyp.server.domain.Account;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupCreateRequest {

  private Account account;
  private List<String> menu;
}
