package westerdals.eksamen.enterprise.backend.ejb;


import org.hibernate.validator.constraints.NotBlank;
import westerdals.eksamen.enterprise.backend.entity.Dish;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by mariu on 07-Jun-17.
 */
@Stateless
public class DishEJB {

    @PersistenceContext
    protected EntityManager em;

    public long createDish(@NotBlank String name, @NotBlank String description) {

        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(description);

        em.persist(dish);

        return dish.getId();
    }

    public List<Dish> getAllDishes() {
        Query query = em.createQuery("select d from Dish d");


        return query.getResultList();
    }

    public Dish getDish(Long id) {
        return em.find(Dish.class, id);
    }
}
