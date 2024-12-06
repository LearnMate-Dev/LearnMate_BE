package LearnMate.dev.repository;

import LearnMate.dev.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan findFirstByUserIdOrderByCreatedAtDesc(Long userId);

    List<Plan> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
