package io.games.api.gamesioapi.model;

import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String name;

    private String nickname;

    private String password;

    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
