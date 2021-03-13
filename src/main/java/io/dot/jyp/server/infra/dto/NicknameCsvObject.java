package io.dot.jyp.server.infra.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameCsvObject {

  @CsvBindByPosition(position = 0)
  private String first;
  @CsvBindByPosition(position = 1)
  private String second;
}
