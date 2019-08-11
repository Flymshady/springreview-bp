package cz.cellar.springreview;

import cz.cellar.springreview.model.Item;
import cz.cellar.springreview.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//Výpis 27
//Integrační test
@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testItemModel(){
        String name = "TestItemName";
        String author = "TestItemAuthor";
        String genre = "TestItemGenre";
        String typeformat = "TestItemTypeFormat";
        int year = 2019;
        String textShort = "TestItemName";
        String textLong = "TestItemName";

        Item item = new Item(name,author,year,typeformat,genre,textShort, textLong);
        testEntityManager.persist(item);
        testEntityManager.flush();

        Item savedItem = itemRepository.getOne(item.getId());
        List<Item> genreList = itemRepository.findByGenreEquals(genre);

        Assert.assertNotNull(savedItem);
        Assert.assertEquals(savedItem.getName(), name);
        Assert.assertEquals(savedItem.getAuthor(), author);
        Assert.assertEquals(savedItem.getGenre(), genre);
        Assert.assertEquals(savedItem.getType(), typeformat);
        Assert.assertEquals(savedItem.getYear(), year);
        Assert.assertEquals(savedItem.getTextLong(), textLong);
        Assert.assertEquals(savedItem.getTextShort(), textShort);
        Assert.assertTrue(genreList.contains(savedItem));
    }
}
