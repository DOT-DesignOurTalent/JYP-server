package io.dot.jyp.server.domain;


import io.dot.jyp.server.domain.Resource.Type;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Permission {
  // Org Permission
  ORGANIZATION_EDIT_PROFILE(Name.ORGANIZATION_EDIT_PROFILE, Type.ORGANIZATION),
  ORGANIZATION_WITHDRAWAL(Name.ORGANIZATION_WITHDRAWAL, Type.ORGANIZATION),

  // Group Permission
  GROUP_CREATE(Name.GROUP_CREATE, Resource.Type.GROUP),
  GROUP_JOIN(Name.GROUP_JOIN, Type.GROUP),
  GROUP_INVITE_USERS(Name.GROUP_INVITE_USERS, Resource.Type.GROUP),
  GROUP_REMOVE(Name.GROUP_REMOVE, Resource.Type.GROUP),
  GROUP_CHANGE_ROLE(Name.GROUP_CHANGE_ROLE, Resource.Type.GROUP),
  GROUP_START_JUDGEMENT(Name.GROUP_START_JUDGEMENT, Resource.Type.GROUP);

  private final String name;
  private final Resource.Type target;

  Permission(String name, Resource.Type target) {
    this.name = name;
    this.target = target;
  }

  public static Permission of(String name) {
    return Arrays.stream(values())
        .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException(String.format("'%s' is not supported permission", name))
        );
  }

  public static class Name {

    public static final String ORGANIZATION_EDIT_PROFILE = "ORGANIZATION_EDIT_PROFILE";
    public static final String ORGANIZATION_WITHDRAWAL = "ORGANIZATION_WITHDRAWAL";

    public static final String GROUP_CREATE = "GROUP_CREATE";
    public static final String GROUP_JOIN = "GROUP_JOIN";
    public static final String GROUP_INVITE_USERS = "GROUP_INVITE_USERS";
    public static final String GROUP_REMOVE = "GROUP_REMOVE";
    public static final String GROUP_CHANGE_ROLE = "GROUP_CHANGE_ROLE";
    public static final String GROUP_START_JUDGEMENT = "GROUP_START_JUDGEMENT";
  }
}
