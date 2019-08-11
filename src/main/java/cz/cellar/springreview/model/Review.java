package cz.cellar.springreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

//Modelová třída pro recenzi s JPA anotacemi
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Item item;
    @NotBlank
    @Column(name="text_short")
    private String textShort;
    @NotBlank
    @Column(name="text_long")
    private String textLong;

    public Review(){}

    public Review(@JsonProperty("person") Person person, @JsonProperty("item") Item item, @JsonProperty("textShort")String textShort, @JsonProperty("textLong") String textLong) {
        this.person = person;
        this.item = item;
        this.textShort = textShort;
        this.textLong = textLong;
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Item getItem() {
        return item;
    }

    public String getTextShort() {
        return textShort;
    }

    public String getTextLong() {
        return textLong;
    }

    public void setTextShort(String textShort) {
        this.textShort = textShort;
    }

    public void setTextLong(String textLong) {
        this.textLong = textLong;
    }

    public void setItem(Item item){
        this.item=item;
    }
    public void setPerson(Person person){
        this.person=person;
    }
}
