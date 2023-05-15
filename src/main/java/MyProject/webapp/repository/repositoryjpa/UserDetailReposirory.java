package MyProject.webapp.repository.repositoryjpa;

import MyProject.webapp.modle.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailReposirory extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
