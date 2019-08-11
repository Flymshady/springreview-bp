package cz.cellar.springreview.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

//Výpis 18
//Modelová třída pro položku s JPA anotacemi
@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="item_id")
    private Long id;
    @Column(name="name")
    @NotBlank
    private String name;
    @Column(name="author")
    @NotBlank
    private String author;
    @Column(name="year")
    private int year;
    @Column(name="type")
    @NotBlank
    private String type;
    @Column(name="genre")
    @NotBlank
    private String genre;
    @Column(name="text_short")
    private String textShort;
    @Column(name="text_long")
    private String textLong;

    public Item(){
    }

    public Item(@JsonProperty("name")String name, @JsonProperty("author")String author,@JsonProperty("year")int year, @JsonProperty("type")String type, @JsonProperty("genre")String genre, @JsonProperty("textShort")String textShort, @JsonProperty("textLong")String textLong) {
        this.name = name;
        this.author=author;
        this.year = year;
        this.type = type;
        this.genre = genre;
        this.textShort = textShort;
        this.textLong = textLong;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getGenre() {
        return genre;
    }

    public String getTextShort() {
        return textShort;
    }

    public String getTextLong() {
        return textLong;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTextShort(String textShort) {
        this.textShort = textShort;
    }

    public void setTextLong(String textLong) {
        this.textLong = textLong;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
