package westerdals.eksamen.enterprise.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mariu on 07-Jun-17.
 */

@Entity
public class Menu {


    @Id
    @GeneratedValue
    private Long id;


    //TODO: fetchype eager to solve some issues, if time look more into this.
    @Size(min = 1)
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private Set<Dish> dishes;

    @NotNull
    @Column(unique = true)
    private LocalDate date;

    public Menu() {
        dishes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
