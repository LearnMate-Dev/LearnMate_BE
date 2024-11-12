package LearnMate.dev.repository;

import LearnMate.dev.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    Plan findFirstByUserIdOrderByCreatedAtDesc(Long userId);

}
