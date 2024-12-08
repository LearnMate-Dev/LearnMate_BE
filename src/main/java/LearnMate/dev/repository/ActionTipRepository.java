package LearnMate.dev.repository;

import LearnMate.dev.model.entity.ActionTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTipRepository extends JpaRepository<ActionTip, Long> {
}
