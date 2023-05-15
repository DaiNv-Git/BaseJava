package MyProject.webapp.repository.repositoryjpa;

import MyProject.webapp.modle.entity.UserEntity;
import MyProject.webapp.modle.entity.WorkingScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkingScheduleRepository extends JpaRepository<WorkingScheduleEntity, Long> {
    List<WorkingScheduleEntity> findByWorkDateBetween(LocalDate startDate, LocalDate endDate);

    List<WorkingScheduleEntity> findByWorkDateBetweenAndUser(LocalDate startDate, LocalDate endDate, UserEntity user);
}
