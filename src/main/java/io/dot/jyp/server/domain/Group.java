package io.dot.jyp.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_group_id"))
    private List<Diner> diners = new ArrayList<>();

    @Column(name = "code", nullable = false)
    private String code;

    @ElementCollection
    private List<String> nicknames = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime created_at;

    private Group(
            List<Diner> diners,
            String code,
            String nickname,
            LocalDateTime created_at
    ) {
        this.diners = diners;
        this.code = code;
        this.nicknames.add(nickname);
        this.created_at = created_at;
    }

    public static Group create(
            List<Diner> diners,
            String code,
            String nickname

    ) {
        return new Group(
                diners,
                code,
                nickname,
                LocalDateTime.now()
        );
    }

    public void addNickname(String nickname) {
        this.nicknames.add(nickname);

    }

    public void addDiners(List<Diner> diners) {
        for (Diner diner : diners) {
            this.diners.add(diner);
        }

    }
}
