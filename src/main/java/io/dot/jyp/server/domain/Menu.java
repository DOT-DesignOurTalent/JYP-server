package io.dot.jyp.server.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Menu {

  KOREAN("한식", 1),
  JAPANESE("일식", 2),
  CHINESE("중식", 3),
  WESTERN("양식", 4),
  SNACK("샌드위치", 5),
  ASIAN("도시락", 6),
  MEXICAN("멕시칸", 7),
  BURGER("햄버거", 8),
  PIZZA("피자", 9),
  CHICKEN("치킨", 10),
  DESSERT("디저트", 11),
  ANYTHING("상관없음", 12);

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
            new IllegalArgumentException(String.format("'%s' is not supported Category", name)));
  }

}
