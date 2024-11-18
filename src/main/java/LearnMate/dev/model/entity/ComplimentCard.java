package LearnMate.dev.model.entity;


import LearnMate.dev.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "compliments")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComplimentCard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compliments_id")
    private Long id;

    @Column(name = "compliments_title", nullable = false)
    private String title;

    @Column(name = "compliments_content", nullable = false)
    private String content;

    @Column(name = "compliments_diary_id", nullable = false)
    private Long diaryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
