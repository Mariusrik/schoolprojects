package westerdals.eksamen.enterprise.backend.ejb;

import westerdals.eksamen.enterprise.backend.entity.Dish;
import westerdals.eksamen.enterprise.backend.entity.Menu;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by mariu on 07-Jun-17.
 */

@Stateless
public class MenuEJB {

    @PersistenceContext
    protected EntityManager em;

    public long createMenu(Set<Dish> dishes, LocalDate date) {

        Menu menu = new Menu();

        menu.setDate(date);
        menu.setDishes(dishes);

        em.persist(menu);

        return menu.getId();
    }

    public Menu getCurrentMenu() {
        LocalDate dateNow = LocalDate.now();

        Query query = em.createQuery("from Menu m WHERE m.date >= :param1 ORDER BY m.date");
        query.setMaxResults(1);
        query.setParameter("param1", dateNow);

        Menu result = getQueryResult(query);
        if (result != null)
            return result;


        query = em.createQuery("From Menu m WHERE m.date < :param1 ORDER BY m.date DESC");
        query.setParameter("param1", dateNow);
        query.setMaxResults(1);

        result = getQueryResult(query);
        return result;
    }

    private Menu getQueryResult(Query query) {

        try {
            Object result = query.getSingleResult();
            return (Menu) result;

        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Menu getNextMenu(LocalDate date) {

        Query query = em.createQuery("from Menu m WHERE m.date > :param1 ORDER BY m.date");
        query.setMaxResults(1);
        query.setParameter("param1", date);

        return getQueryResult(query);
    }

    public Menu getPreviousMenu(LocalDate date) {

        Query query = em.createQuery("From Menu m WHERE m.date < :param1 ORDER BY m.date DESC");
        query.setMaxResults(1);
        query.setParameter("param1", date);

        return getQueryResult(query);
    }


    public Menu getMenu(Long id) {
        return em.find(Menu.class, id);
    }
}
