package io.dot.jyp.server.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupJoinWithCodeRequest {

  private String code;
  private List<String> menu;
}
