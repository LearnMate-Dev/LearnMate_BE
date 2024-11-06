package LearnMate.dev.repository;

import LearnMate.dev.model.entity.Diary;
import LearnMate.dev.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT COUNT(d) > 0 " +
            "FROM Diary d " +
            "WHERE d.user = :user " +
            "AND DATE(d.createdAt) = :created_at")
    boolean existsDiaryByCreatedAt(
            @Param(value = "user") User user,
            @Param(value = "created_at") LocalDate localDate);
}
