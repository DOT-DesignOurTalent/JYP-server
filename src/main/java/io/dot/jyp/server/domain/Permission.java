package io.dot.jyp.server.domain;


import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Permission {
  // Org Operation Permission

  // Common Group Permission
  GROUP_CREATE(Name.GROUP_CREATE, Resource.Type.GROUP),
  GROUP_INVITE_USERS(Name.GROUP_INVITE_USERS, Resource.Type.GROUP),
  GROUP_RE_INVITE_USERS(Name.GROUP_RE_INVITE_USERS, Resource.Type.GROUP),
  GROUP_DECLINE_INVITE(Name.CANCEL_INVITE_GROUP_ACCOUNT, Resource.Type.GROUP),
  GROUP_REMOVE(Name.GROUP_REMOVE, Resource.Type.GROUP),
  GROUP_CHANGE_USERS_ROLE(Name.GROUP_CHANGE_USERS_ROLE, Resource.Type.GROUP),

  GAME_READY(Name.GAME_READY, Resource.Type.GAME),
  GAME_START(Name.GAME_START, Resource.Type.GAME),
  GAME_RE_START(Name.GAME_RE_START, Resource.Type.GAME),
  GAME_REQUEST_APPEAL(Name.GAME_REQUEST_APPEAL, Resource.Type.GAME),
  GAME_APPROVE_APPEAL(Name.GAME_APPROVE_APPEAL, Resource.Type.GAME),
  GAME_DECLINE_APPEAL(Name.GAME_DECLINE_APPEAL, Resource.Type.GAME);

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

    public static final String GROUP_CREATE = "GROUP_CREATE";
    public static final String GROUP_INVITE_USERS = "GROUP_INVITE_USERS";
    public static final String GROUP_RE_INVITE_USERS = "GROUP_RE_INVITE_USERS";
    public static final String CANCEL_INVITE_GROUP_ACCOUNT = "CANCEL_INVITE_GROUP_ACCOUNT";
    public static final String GROUP_REMOVE = "GROUP_REMOVE";
    public static final String GROUP_CHANGE_USERS_ROLE = "GROUP_CHANGE_USERS_ROLE";

    public static final String GAME_READY = "GAME_READY";
    public static final String GAME_START = "GAME_START";
    public static final String GAME_RE_START = "GAME_RE_START";
    public static final String GAME_REQUEST_APPEAL = "GAME_REQUEST_APPEAL";
    public static final String GAME_APPROVE_APPEAL = "GAME_APPROVE_APPEAL";
    public static final String GAME_DECLINE_APPEAL = "GAME_DECLINE_APPEAL";
  }
}
