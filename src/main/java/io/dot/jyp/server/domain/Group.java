package io.dot.jyp.server.domain;


import io.dot.jyp.server.domain.GroupMessage.MessageType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Before;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class Group extends DomainEntity {

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  @JoinTable(
      name = "group_menu",
      joinColumns = @JoinColumn(name = "group_id"),
      foreignKey = @ForeignKey(name = "fk_group_id"),
      indexes = @Index(name = "group_menu_group_id", columnList = "group_id")
  )
  @Column(name = "menu")
  private List<Menu> menus;

  @ElementCollection
  @Column(name = "position")
  private List<Integer> position;

  @Column(name = "code", nullable = false)
  private String code;

  public void tempCreate()
  {
    List<Menu> tempMenu = new ArrayList<>();
    tempMenu.add(Menu.KOREAN);
    Group group = Group.create(
        tempMenu,
        "testCode"
    );
  }

  private Group(
      List<Menu> menus,
      String code
  ) {
    this.menus = menus;
    this.code = code;
    this.position = Arrays.asList(new Integer[]{1,2,3,4,5,6});
  }

  public static Group create(
      List<Menu> menus,
      String code
  ) {
    return new Group(
        menus,
        code
    );
  }

  public void addMenu(List<Menu> menus) {
    this.menus.addAll(
        menus.stream()
            .filter(menu -> !this.menus.contains(menu))
            .collect(Collectors.toList())
    );
  }

  public GroupMessage handleGroupMessage(GroupMessage groupMessage){
    if(groupMessage.getType().equals(MessageType.ENTER)){
      String message = "";
      for (Menu menu : this.menus){
        message = menu.getName()+",";
      }
      groupMessage.setMessage(message);
    }
    return groupMessage;
  }
}
