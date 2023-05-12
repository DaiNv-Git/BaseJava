package MyProject.webapp.repository.repositoryjpa;

import MyProject.webapp.modle.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Long> {
}
