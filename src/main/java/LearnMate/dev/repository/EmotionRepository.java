package LearnMate.dev.repository;

import LearnMate.dev.model.dto.response.ReportResponse;
import LearnMate.dev.model.entity.Emotion;
import LearnMate.dev.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    @Query("SELECT new LearnMate.dev.model.dto.response.ReportResponse$EmotionDto(e.emotion, COUNT(e)) " +
            "FROM Emotion e " +
            "JOIN e.diary d " +
            "WHERE d.user = :user " +
            "AND DATE(e.createdAt) >= :startDate " +
            "AND DATE(e.createdAt) <= :endDate " +
            "GROUP BY e.emotion ")
    List<ReportResponse.EmotionDto> findEmotionDtoByUser(
            @Param(value = "startDate") LocalDate startDate,
            @Param(value = "endDate") LocalDate endDate,
            @Param(value = "user") User user);

    @Query("SELECT new LearnMate.dev.model.dto.response.ReportResponse$EmotionOnDayDto(e.emotion, e.createdAt) " +
            "FROM Emotion e " +
            "JOIN e.diary d " +
            "WHERE d.user = :user " +
            "AND DATE(e.createdAt) >= :startDate " +
            "AND DATE(e.createdAt) <= :endDate " +
            "ORDER BY e.createdAt asc ")
    List<ReportResponse.EmotionOnDayDto> findEmotionOnDayDtoByUser(
            @Param(value = "startDate") LocalDate startDate,
            @Param(value = "endDate") LocalDate endDate,
            @Param(value = "user") User user);
}
