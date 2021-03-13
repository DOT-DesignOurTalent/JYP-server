package io.dot.jyp.server.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ElementCollection
  private List<String> menu = new ArrayList<>();

  @Column(name = "code", nullable = false)
  private String code;

  @ElementCollection
  private List<String> nicknames = new ArrayList<>();

  @Column(name = "created_at")
  private LocalDateTime created_at;

  private Group(
      List<String> menu,
      String code,
      String nickname,
      LocalDateTime created_at
  ) {
    this.menu = menu;
    this.code = code;
    this.nicknames.add(nickname);
    this.created_at = created_at;
  }

  public static Group create(
      List<String> category,
      String code,
      String nickname

  ) {
    return new Group(
        category,
        code,
        nickname,
        LocalDateTime.now()
    );
  }

  public void addNickname(String nickname) {
    this.nicknames.add(nickname);
  }

  public void addMenu(List<String> menu) {
    this.menu.addAll(menu);
  }
}
