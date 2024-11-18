package LearnMate.dev.repository;

import LearnMate.dev.model.entity.ComplimentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplimentCardRepository extends JpaRepository<ComplimentCard, Long> {

    List<ComplimentCard> findAllByUserId(Long userId);

}
