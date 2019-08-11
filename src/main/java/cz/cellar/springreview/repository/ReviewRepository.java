package cz.cellar.springreview.repository;

import cz.cellar.springreview.model.Item;
import cz.cellar.springreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Repository pro Review
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByItemId(Long itemId);

    Optional<Review> findByIdAndItemId(Long id, Long itemId);
    Optional<Review> findByIdAndPersonId(Long id, Long personId);
}
