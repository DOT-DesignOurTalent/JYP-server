package io.dot.jyp.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Column(name = "group_code", nullable = false)
    private String groupCode;

    @ElementCollection
    private List<String> nicknames = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime created_at;

    private Group(
            List<Diner> diners,
            String groupCode,
            String nickname,
            LocalDateTime created_at
    ){
        this.diners=diners;
        this.groupCode = groupCode;
        this.nicknames.add(nickname);
        this.created_at = created_at;
    }

    public static String generateGroupCode(){
        Random random = new Random();
        int length = 6;
        return  random.ints(48,122)
                .filter(i-> (i<57 || i>65) && (i <90 || i>97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static Group groupCreate(
            List<Diner> diners,
            String groupCode,
            String nickname

    ){
        return new Group(
                diners,
                groupCode,
                nickname,
                LocalDateTime.now()
        );
    }

    public void addNickname(String nickname){
        this.nicknames.add(nickname);

    }
    public void addDiners(List<Diner> diners){
        for(Diner diner : diners){ this.diners.add(diner); }

    }
}
