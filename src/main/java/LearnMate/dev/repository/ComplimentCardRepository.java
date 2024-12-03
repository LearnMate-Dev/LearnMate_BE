package LearnMate.dev.repository;

import LearnMate.dev.model.entity.ComplimentCard;
import LearnMate.dev.model.enums.ComplimentKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplimentCardRepository extends JpaRepository<ComplimentCard, Long> {

    List<ComplimentCard> findAllByUserId(Long userId);

    Optional<ComplimentCard> findByKeyword(ComplimentKeyword keyword);

}
