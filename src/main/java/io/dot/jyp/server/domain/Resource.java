package io.dot.jyp.server.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Resource {

  @Enumerated(EnumType.STRING)
  private Type type;

  public static Resource of(Type type) {
    return new Resource(type);
  }

  public static Resource of(String type) {
    return of(Type.of(type));
  }

  public static Resource organization() {
    return new Resource(Type.ORGANIZATION);
  }

  public static Resource group() {
    return new Resource(Type.GROUP);
  }

  public boolean isOrganization() {
    return this.type.equals(Type.ORGANIZATION);
  }

  public boolean isGroup() {
    return this.type.equals(Type.GROUP);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }

  @Schema(name = "ResourceType", enumAsRef = true)
  @Getter
  public enum Type {
    ORGANIZATION("organization"),
    GROUP("group"),
    GAME("game");

    private final String name;

    Type(String name) {
      this.name = name;
    }

    public static Type of(String name) {
      return Arrays.stream(values())
          .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
          .findFirst()
          .orElseThrow(() ->
              new IllegalArgumentException(
                  String.format("'%s' is not supported resource type", name))
          );
    }
  }
}