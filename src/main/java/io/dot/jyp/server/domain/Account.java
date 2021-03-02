package io.dot.jyp.server.domain;

import io.dot.jyp.server.domain.exception.AuthenticationException;
import io.dot.jyp.server.domain.exception.BadRequestException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "passphrase", nullable = false)
    private String passphrase;

    @Column(name = "nickname")
    private String nickname;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_account_id"))
    private List<Role> roles = new ArrayList<>();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    private Account(
            String email,
            String passphrase,
            Status status,
            List<Role> roles,
            LocalDateTime created_at
    ) {
        this.email = email;
        this.passphrase = passphrase;
        this.status = status;
        this.addRoles(roles);
        this.created_at = created_at;
    }

    public static Account signup(
            String email,
            String passphrase
    ) {
        return new Account(
                email,
                passphrase,
                Status.ACTIVE,
                Collections.singletonList(
                        Role.of(
                                Role.Name.ORGANIZATION_USER,
                                Arrays.asList(
                                        Permission.GROUP_CREATE,
                                        Permission.GROUP_INVITE_USERS
                                )
                        )
                ),
                LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        );
    }

    public void addRoles(List<Role> newRoles) {
        newRoles.forEach(this::addRole);
    }

    public void addRole(Role role) {
        this.checkRoleAddable(role);
        role.setAccount(this);
        this.roles.add(role);
    }

    private void checkRoleAddable(Role role) {
        if (this.getMatchedRole(role.getResource(), role.getName()).isPresent()) {
            throw new BadRequestException(String.format(
                    "account '%s' already has role '%s' at '%s'",
                    this.getEmail(),
                    role.getName(),
                    role.getResource().getType()
            ));
        }
        if (role.getResource().isGroup() && this.hasAnyRoleOf(role.getResource())) {
            throw new BadRequestException(
                    String.format("account '%s' already has role of group '%s'", this.getEmail(), role.getResource())
            );
        }
    }

    public Optional<Role> getMatchedRole(Resource resource, Role.Name roleName) {
        return this.roles.stream()
                .filter(role -> role.isSameResource(resource) && role.isSameRole(roleName))
                .findFirst();
    }

    public boolean hasAnyRoleOf(Resource resource) {
        return this.roles.stream()
                .anyMatch(role -> role.getResource().equals(resource));
    }

    public void updatePassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public boolean isActive() {
        return this.status.equals(Status.ACTIVE);
    }

    public boolean isAdmin() {
        return this.roles.stream().anyMatch(Role::isAdmin);
    }

    public boolean isDeleted() {
        return this.status.equals(Status.DELETED);
    }

    public Account deleteAccount(Account account) {
        if (this.equals(account)) {
            throw new BadRequestException("you cannot delete yourself");
        }

        if (this.isAdmin()) {
            throw new AuthenticationException("there is no authority");
        }

        if (!this.isActive()) {
            throw new BadRequestException(String.format("account '%s' is not active", this.getId()));
        }
        account.delete();
        return account;
    }

    public void deleteAllRolesByResource(Resource resource) {
        if (this.isAdmin()) {
            throw new AuthenticationException("cannot access wallet operator's wallet role");
        }
        this.roles.removeIf(role -> role.getResource().equals(resource));
    }

    private void delete() {
        if (!this.isActive()) {
            throw new BadRequestException("you can delete only active account");
        }

        this.status = Status.DELETED;
        this.passphrase = null;
        this.roles.clear();
    }

    public void deleteMatchedRole(Resource resource, Role.Name roleName) {
        this.roles.removeIf(role ->
                role.isSameResource(resource) && role.isSameRole(roleName)
        );
    }

    @Schema(name = "AccountStatus", enumAsRef = true)
    public enum Status {
        ACTIVE("active"),
        INACTIVE("inactive"),
        DELETED("deleted");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public static Account.Status of(String name) {
            return Arrays.stream(values())
                    .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    String.format("'%s' is not supported account status", name)));
        }
    }
}
