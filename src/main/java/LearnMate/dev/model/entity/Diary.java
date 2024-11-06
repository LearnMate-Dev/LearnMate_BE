package LearnMate.dev.model.entity;

import LearnMate.dev.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Builder
@Table(name = "diaries")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL)
    private Emotion emotion;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL)
    private ActionTip actionTip;

}
