package cz.cellar.springreview.repository;

import cz.cellar.springreview.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Výpis 19
//Repository pro Person
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String username);
    Boolean existsByUsername(String username);

}
