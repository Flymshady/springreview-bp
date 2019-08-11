package cz.cellar.springreview.view;

import cz.cellar.springreview.model.CustomUserDetails;
import cz.cellar.springreview.repository.ItemRepository;
import cz.cellar.springreview.repository.PersonRepository;
import cz.cellar.springreview.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

//Výpis 17
//Controller třída
@Controller
public class ViewController {


    private String appMode;
    private ReviewRepository reviewRepository;
    private ItemRepository itemRepository;
    private PersonRepository personRepository;
    //constructor injection pro app-mode (z application.properties)
    @Autowired
    public ViewController(Environment environment, PersonRepository personRepository, ReviewRepository reviewRepository, ItemRepository itemRepository){
        appMode=environment.getProperty("app-mode");
        this.reviewRepository = reviewRepository;
        this.itemRepository = itemRepository;
        this.personRepository = personRepository;
    }

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        return "index";
    }

    @RequestMapping("/admin")
    public String admin(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        return "admin";
    }

    @RequestMapping("/item/{id}")
    public String item(Model model, @PathVariable Long id){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        model.addAttribute("id", id);
        return "item";
    }

    @RequestMapping("/item/{id}/reviews")
    public String reviews(Model model, @PathVariable Long id){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        model.addAttribute("id", id);
        return "reviews";
    }

    @RequestMapping("/item/{id}/reviews/create")
    public String create(Model model, @PathVariable Long id){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        model.addAttribute("id", id);
        return "createReview";
    }

    @RequestMapping("/admin/item/{id}")
    public String adminItem(Model model, @PathVariable Long id){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        model.addAttribute("id", id);
        return "adminItem";
    }

    @RequestMapping("/admin/create")
    public String create(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        return "createItem";
    }

    @RequestMapping("/admin/update/{id}")
    public String updateItem(Model model, @PathVariable Long id){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        model.addAttribute("id", id);
        return "updateItem";
    }
    @RequestMapping("/register")
    public String register(Model model){

            model.addAttribute("datetime", new Date());
            model.addAttribute("mode", appMode);
            return "register";

    }
    @RequestMapping("/item/{itemId}/reviews/update/{reviewId}")
    public String updateReview(Model model, @PathVariable Long itemId, @PathVariable Long reviewId,@AuthenticationPrincipal CustomUserDetails user ){
        model.addAttribute("datetime", new Date());
        model.addAttribute("mode", appMode );
        model.addAttribute("itemId", itemId);
        model.addAttribute("reviewId", reviewId);
        if(user==null){
            return "unauthorized";
        }
        if(!reviewRepository.findById(reviewId).equals(reviewRepository.findByIdAndPersonId(reviewId, user.getId()))){
            return "unauthorized";
        }
        return "updateReview";
    }
}
