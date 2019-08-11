package cz.cellar.springreview.repository;

import cz.cellar.springreview.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Repository pro Item
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    //jpa repository metoda
    List<Item> findByGenreEquals(String genre);
}
