package MyProject.webapp.repository.repositoryjpa;

import MyProject.webapp.modle.entity.UserEntity;
import MyProject.webapp.modle.entity.WorkingScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkingScheduleRepository extends JpaRepository<WorkingScheduleEntity, Long> {
    List<WorkingScheduleEntity> findByWorkDateBetween(LocalDate startDate, LocalDate endDate);

    List<WorkingScheduleEntity> findByWorkDateBetweenAndUser(LocalDate startDate, LocalDate endDate, UserEntity user);

    @Query("SELECT ws FROM WorkingScheduleEntity ws " +
            "JOIN ws.user u " +
            "WHERE ws.workDate BETWEEN :startDate AND :endDate " +
            "AND (:fullName IS NULL OR u.fullName LIKE %:fullName%)")
    List<WorkingScheduleEntity> findWorkingSchedulesByDateAndFullName(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("fullName") String fullName
    );
}
