package MyProject.webapp.repository.repositoryjpa;

import MyProject.webapp.modle.entity.WorkTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkTypeEntity, Long> {
}
