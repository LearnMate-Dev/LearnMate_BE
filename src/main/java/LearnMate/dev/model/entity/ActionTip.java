package LearnMate.dev.model.entity;

import LearnMate.dev.common.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Builder
@Table(name = "actiontips")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionTip extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_tip_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public void updateDiary(Diary diary) {
        this.diary = diary;
    }
}
