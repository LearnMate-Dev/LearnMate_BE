package LearnMate.dev.repository;

import LearnMate.dev.model.entity.ComplimentCard;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.model.enums.ComplimentKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplimentCardRepository extends JpaRepository<ComplimentCard, Long> {

    @Query(value = "SELECT c " +
            "FROM ComplimentCard c " +
            "WHERE c.user.id = :userId " +
            "AND c.keyword != 'NO_KEYWORD' " +
            "ORDER BY c.createdAt desc")
    List<ComplimentCard> findAllByUserId(@Param(value = "userId") Long userId);

    Optional<ComplimentCard> findByKeywordAndUser(ComplimentKeyword keyword, User user);

}
