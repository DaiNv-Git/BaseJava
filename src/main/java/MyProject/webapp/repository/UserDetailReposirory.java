package MyProject.webapp.repository;

import MyProject.webapp.modle.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailReposirory extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "select u from UserEntity u where :#{#email} IS NULL OR u.email LIKE %:#{#email}%")
    List<UserEntity> findAll(String email);
}
