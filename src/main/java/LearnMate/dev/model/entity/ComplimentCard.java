package LearnMate.dev.model.entity;

import LearnMate.dev.common.base.BaseTimeEntity;
import LearnMate.dev.model.enums.ComplimentKeyword;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "compliments_keyword", nullable = false)
    private ComplimentKeyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
