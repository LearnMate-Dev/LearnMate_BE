package LearnMate.dev.model.entity;

import LearnMate.dev.model.enums.EmotionSpectrum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "emotions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emotion_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private Double score; // 감정 점수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmotionSpectrum emotion; // 감정 지표

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;
}
