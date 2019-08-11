package cz.cellar.springreview.repository;

import cz.cellar.springreview.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository pro Role
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
