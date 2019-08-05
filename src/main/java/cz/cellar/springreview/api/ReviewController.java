package cz.cellar.springreview.api;

import cz.cellar.springreview.exception.ResourceNotFoundException;
import cz.cellar.springreview.exception.UnauthorizedAccessException;
import cz.cellar.springreview.model.CustomUserDetails;
import cz.cellar.springreview.model.Review;
import cz.cellar.springreview.repository.ItemRepository;
import cz.cellar.springreview.repository.PersonRepository;
import cz.cellar.springreview.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reviews/")
public class ReviewController {


    private ReviewRepository reviewRepository;
    private ItemRepository itemRepository;
    private PersonRepository personRepository;

    @Autowired
    public ReviewController(PersonRepository personRepository, ReviewRepository reviewRepository, ItemRepository itemRepository) {
        this.reviewRepository = reviewRepository;
        this.itemRepository = itemRepository;
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Review> getAll(){
        return reviewRepository.findAll();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Review getById(@PathVariable( value = "id") Long id){
        return reviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Review", "id", id));

    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
    public List<Review> getByItemId(@PathVariable( value = "itemId") Long itemId){
        return reviewRepository.findByItemId(itemId);
    }

    @RequestMapping(value = "/create/{itemId}", method = RequestMethod.POST)
    public @ResponseBody Review create(@PathVariable(value = "itemId") Long itemId,
                                       @Valid @NonNull @RequestBody Review review,
                                       @AuthenticationPrincipal CustomUserDetails user){

            return itemRepository.findById(itemId).map(item -> {
                review.setItem(item);
                return personRepository.findById(user.getId()).map(person -> {
                    review.setPerson(person);
                    return reviewRepository.save(review);
                }).orElseThrow(() -> new UnauthorizedAccessException(user.getUsername()));
            }).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

    }

    @RequestMapping(value = "/remove/{reviewId}/items/{itemId}", method = RequestMethod.DELETE)
    public List<Review> remove(@PathVariable(value = "reviewId") Long reviewId,
                                 @PathVariable(value = "itemId") Long itemId,
                                    @AuthenticationPrincipal CustomUserDetails user) {

        return reviewRepository.findById(reviewId).map(review -> {
            return reviewRepository.findByIdAndItemId(reviewId, itemId).map(review1 -> {
                if(!user.getRoleString().equals("ADMIN")) {
                    return reviewRepository.findByIdAndPersonId(reviewId, user.getId()).map(review2 -> {
                        reviewRepository.delete(review2);
                        return getByItemId(itemId);
                    }).orElseThrow(() -> new UnauthorizedAccessException(user.getUsername()));
                }else {
                    reviewRepository.delete(review1);
                    return getByItemId(itemId);
                }
            }).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        }).orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
    }




    @RequestMapping(value = "/update/{reviewId}/items/{itemId}", method = RequestMethod.PUT)
    public Review update(@PathVariable(value = "reviewId") Long reviewId,
                         @PathVariable(value = "itemId") Long itemId,
                        @AuthenticationPrincipal CustomUserDetails user,
                       @Valid @RequestBody Review reviewRequest){

        return reviewRepository.findById(reviewId).map(review -> {
            return reviewRepository.findByIdAndItemId(reviewId, itemId).map(review1 -> {
                return reviewRepository.findByIdAndPersonId(reviewId, user.getId()).map(review2 -> {
                    review2.setTextShort(reviewRequest.getTextShort());
                    review2.setTextLong(reviewRequest.getTextLong());
                    return reviewRepository.save(review2);
                }).orElseThrow(()->new UnauthorizedAccessException(user.getUsername()));
            }).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        }).orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
    }
}
