package io.dot.jyp.server.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles", indexes = {
    @Index(name = "roles_account_id", columnList = "account_id"),
})
@Getter
@Setter
@NoArgsConstructor
public class Role extends DomainEntity {

  @Column(name = "name", nullable = false)
  @Enumerated(EnumType.STRING)
  private Name name;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "id", column = @Column(name = "resource_id", nullable = false)),
      @AttributeOverride(name = "type", column = @Column(name = "resource_type", nullable = false))
  })
  private Resource resource;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  @JoinTable(
      name = "role_permissions",
      joinColumns = @JoinColumn(name = "role_id"),
      foreignKey = @ForeignKey(name = "fk_role_id"),
      indexes = @Index(name = "role_permissions_role_id", columnList = "role_id")
  )
  @Column(name = "permission", nullable = false)
  private List<Permission> permissions;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  private Role(Name name, Resource resource) {
    this.name = name;
    this.resource = resource;
  }

  public Role(Name name, List<Permission> permissions) {
    this.name = name;
    this.resource = new Resource(name.getTarget());
    this.permissions = permissions;
  }

  public static Role of(Name name, List<Permission> permissions) {
    return new Role(name, permissions);
  }

  public static String formatToGrantedAuthority(Resource resource, Permission permission) {
    return String.format("%s_%s", resource.getType(), permission);
  }

  public List<String> getGrantedAuthorities() {
    return this.permissions
        .stream()
        .map(permission -> formatToGrantedAuthority(this.resource, permission))
        .collect(Collectors.toList());
  }

  public void updatePermissions(List<Permission> permissions) {
    this.permissions.removeIf(permission -> !permissions.contains(permission));
    this.permissions.addAll(
        permissions.stream()
            .filter(permission -> !this.permissions.contains(permission))
            .collect(Collectors.toList())
    );
  }

  public boolean isAdmin() {
    return this.isSameRole(Name.ORGANIZATION_ADMIN);
  }

  public boolean isManager() {
    return this.isSameRole(Name.ORGANIZATION_MANAGER);
  }

  public boolean isUser() {
    return this.isSameRole(Role.Name.ORGANIZATION_USER);
  }

  public boolean isHost() {
    return this.isSameRole(Name.GROUP_HOST);
  }

  public boolean isGuest() {
    return this.isSameRole(Name.GROUP_GUEST);
  }

  public boolean isSameRole(Name name) {
    return this.name.equals(name);
  }

  public boolean isSameResource(Resource resource) {
    return this.resource.equals(resource);
  }

  @Schema(name = "role_name", enumAsRef = true)
  @Getter
  public enum Name {
    ORGANIZATION_ADMIN("ORGANIZATION_ADMIN", Resource.Type.ORGANIZATION),
    ORGANIZATION_MANAGER("ORGANIZATION_MANAGER", Resource.Type.ORGANIZATION),
    ORGANIZATION_USER("ORGANIZATION_USER", Resource.Type.ORGANIZATION),
    ORGANIZATION_GUEST("ORGANIZATION_GUEST", Resource.Type.ORGANIZATION),

    GROUP_HOST("GROUP_HOST", Resource.Type.GROUP),
    GROUP_GUEST("GROUP_GUEST", Resource.Type.GROUP);

    private final String name;
    private final Resource.Type target;

    Name(String name, Resource.Type target) {
      this.name = name;
      this.target = target;
    }

    public static Role.Name of(String name) {
      return Arrays.stream(values())
          .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
          .findFirst()
          .orElseThrow(() ->
              new IllegalArgumentException(String.format("'%s' is not supported role.", name))
          );
    }
  }
}