package LearnMate.dev.model.entity;

import LearnMate.dev.common.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Builder
@Table(name = "plans")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @Column(name = "plan_content", length = 300, nullable = false)
    private String content;

    @Column(name = "plan_guide", nullable = false)
    private String guide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateContentAndGuide(String content, String guide) {
        if (!content.isEmpty()) {
            this.content = content;
            this.guide = guide;
        }
    }
}
