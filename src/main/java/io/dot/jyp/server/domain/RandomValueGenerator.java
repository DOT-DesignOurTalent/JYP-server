package io.dot.jyp.server.domain;

import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomValueGenerator {

  private List<String> first;
  private List<String> second;

  private RandomValueGenerator(List<String> first, List<String> second) {
    this.first = first;
    this.second = second;
  }

  public static RandomValueGenerator of(List<String> first, List<String> second) {
    return new RandomValueGenerator(
        first,
        second
    );
  }

  public static int generateRandomNumber(int length) {
    return new Random().nextInt(length);
  }

  public String generateRandomCode() {
    int length = 6;
    return new Random().ints(48, 122)
        .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
        .mapToObj(i -> (char) i)
        .limit(length)
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
        .toString();
  }

  public String generateRandomText() {
    return first.get(generateRandomNumber(first.size())) + " "
        + second.get(generateRandomNumber(second.size()));
  }
}
