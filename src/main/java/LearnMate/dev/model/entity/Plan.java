package LearnMate.dev.model.entity;

import LearnMate.dev.common.BaseTimeEntity;
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

    @Column(length = 300, nullable = false)
    private String content;

    @Column(nullable = false)
    private String guide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateContent(String content) {
        if (!content.isEmpty()) {
            this.content = content;
        }
    }

}
