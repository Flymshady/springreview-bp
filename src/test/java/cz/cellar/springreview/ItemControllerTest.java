package cz.cellar.springreview;

import cz.cellar.springreview.api.ItemController;
import cz.cellar.springreview.repository.ItemRepository;
import cz.cellar.springreview.repository.PersonRepository;
import cz.cellar.springreview.repository.ReviewRepository;
import cz.cellar.springreview.repository.RoleRepository;
import cz.cellar.springreview.service.CustomUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Collections;

//Výpis 25, 26
//Unit test třída
@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private ReviewRepository reviewRepository;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    //Testovací metoda pro získání položek
    @Test
    public void test() throws Exception {
     Mockito.when(itemRepository.findAll()).thenReturn(
             Collections.emptyList()
     );
        MvcResult mvcResult = mockMvc.perform(
        MockMvcRequestBuilders.get("/api/items/all")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse());
        Mockito.verify(itemRepository).findAll();
    }

}
