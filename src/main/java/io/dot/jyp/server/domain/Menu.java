package io.dot.jyp.server.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Menu {

  KOREAN("한식",1),
  JAPANESE("일식",2),
  CHINESE("중식",3),
  BURGER("햄버거",4),
  SANDWICH("샌드위치",5),
  LUNCHEON_BOX("도시락", 6),
  PORK_CUTLET("돈까스", 7),
  SNACK("분식", 8),
  PIZZA("피자", 9),
  CHICKEN("치킨", 10),
  ASIAN("아시안", 11),
  SASHIMI("회",12),
  SUSHI("초밥",13),
  DESSERT("디저트",14);

  private final String name;
  private final Integer index;

  Menu(String name, Integer index) {
    this.index = index;
    this.name = name;
  }

  public static Menu of(String name) {
    return Arrays.stream(values())
        .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException(String.format("'%s' is not supported blockchain", name)));
  }

}
