package cz.cellar.springreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name= "person")
public class Person {

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;
    @NotBlank
    @Column(name = "password")
    private String password;
    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Role role;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    public Person(){}

   /* public Person(Person person){
        this.role=person.getRole();
        this.name=person.getName();
        this.username=person.getUsername();
        this.password=person.getPassword();
        this.id=person.getId();
    }*/

    public Person(@JsonProperty(value = "username") String username, @JsonProperty(value = "password") String password, @JsonProperty(value = "name") String name,  Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role=  role;
    }
    public Person(@JsonProperty(value = "username") String username, @JsonProperty(value = "password") String password, @JsonProperty(value = "name") String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
