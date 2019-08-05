package cz.cellar.springreview.api;

import cz.cellar.springreview.exception.ResourceNotFoundException;
import cz.cellar.springreview.model.Review;
import cz.cellar.springreview.repository.ItemRepository;
import cz.cellar.springreview.model.Item;
import cz.cellar.springreview.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/items/")
@RestController
public class ItemController {

    private ItemRepository itemRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository, ReviewRepository reviewRepository){
       this.itemRepository=itemRepository;
       this.reviewRepository=reviewRepository;

    }
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Item> getAll(){
        return itemRepository.findAll();
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Item getById(@PathVariable(value = "id") Long id){
        return itemRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Item", "id", id));
    }

    @RequestMapping(value = "/genre/{genre}", method = RequestMethod.GET)
    public List<Item> getByGenre(@PathVariable String genre){
        return itemRepository.findByGenreEquals(genre);
    }


    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public @ResponseBody Item create(@Valid @NonNull @RequestBody Item item){
       return itemRepository.save(item);

    }

    @RequestMapping(value = "/admin/remove/{id}", method = RequestMethod.DELETE)
    public List<Item> remove(@PathVariable(value = "id") Long id){
        Item item = itemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Item", "id", id));


        for (Review review : reviewRepository.findByItemId(id)){
            reviewRepository.delete(review);
        }
        itemRepository.delete(item);
        return itemRepository.findAll();

    }

    @RequestMapping(value = "/admin/update/{id}", method = RequestMethod.PUT)
    public Item update(@PathVariable(value = "id") Long id,
        @Valid @RequestBody Item itemDetails){
        Item item = itemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Item", "id", id));
        item.setName(itemDetails.getName());
        item.setAuthor(itemDetails.getAuthor());
        item.setYear(itemDetails.getYear());
        item.setGenre(itemDetails.getGenre());
        item.setType(itemDetails.getType());
        item.setTextShort(itemDetails.getTextShort());
        item.setTextLong(itemDetails.getTextLong());

        Item updatedItem = itemRepository.save(item);
        return updatedItem;


    }

}
