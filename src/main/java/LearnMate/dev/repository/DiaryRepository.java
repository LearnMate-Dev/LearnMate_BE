package LearnMate.dev.repository;

import LearnMate.dev.model.dto.response.DiaryCalendarResponse;
import LearnMate.dev.model.entity.Diary;
import LearnMate.dev.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT COUNT(d) > 0 " +
            "FROM Diary d " +
            "WHERE d.user = :user " +
            "AND DATE(d.createdAt) = :created_at")
    boolean existsDiaryByCreatedAt(
            @Param(value = "user") User user,
            @Param(value = "created_at") LocalDate localDate);

    @Query("SELECT d " +
            "FROM Diary d " +
            "JOIN FETCH d.emotion e " +
            "JOIN FETCH d.actionTip ac " +
            "WHERE d.user.id = :userId " +
            "AND DATE(d.createdAt) = :now")
    Diary findDiaryCreatedAtNowByUserId(
            @Param(value = "now") LocalDate now,
            @Param(value = "userId") Long userId);

    @Query("SELECT new LearnMate.dev.model.dto.response.DiaryCalendarResponse$DiaryDto(d.id, d.createdAt, e.emotion) " +
            "FROM Diary d " +
            "JOIN d.emotion e " +
            "WHERE d.user.id = :userId " +
            "AND YEAR(d.createdAt) = YEAR(:now) " +
            "AND MONTH(d.createdAt) = MONTH(:now) " +
            "ORDER BY d.createdAt")
    List<DiaryCalendarResponse.DiaryDto> findDiaryCreatedAtMonth(
            @Param(value = "now") LocalDate now,
            @Param(value = "userId") Long userId);
}
