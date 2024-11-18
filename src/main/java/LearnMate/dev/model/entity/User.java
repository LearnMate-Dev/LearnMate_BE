package LearnMate.dev.model.entity;

import LearnMate.dev.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_login_id", nullable = false)
    private String loginId;

    @Column(name = "user_password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Diary> diaryList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Plan> planList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ComplimentCard> complimentCardList;


    @Builder
    public User(String name, String loginId, String password) {

        this.name = name;
        this.loginId = loginId;
        this.password = password;

    }

}
