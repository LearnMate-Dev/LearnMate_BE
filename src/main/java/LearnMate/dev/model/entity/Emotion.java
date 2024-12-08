package LearnMate.dev.model.entity;

import LearnMate.dev.common.base.BaseTimeEntity;
import LearnMate.dev.model.enums.EmotionSpectrum;
import jakarta.persistence.*;
import lombok.*;

@Entity @Builder
@Getter
@Table(name = "emotions")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emotion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emotion_id")
    private Long id;

    @Column(name = "emotion_score", length = 500, nullable = false)
    private Double score; // 감정 점수

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion_emotion", nullable = false)
    private EmotionSpectrum emotion; // 감정 지표

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public void updateDiary(Diary diary) {
        this.diary = diary;
    }
}
