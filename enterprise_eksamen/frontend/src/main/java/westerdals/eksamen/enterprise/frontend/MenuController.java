package westerdals.eksamen.enterprise.frontend;

import westerdals.eksamen.enterprise.backend.ejb.MenuEJB;
import westerdals.eksamen.enterprise.backend.entity.Dish;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by mariu on 07-Jun-17.
 */

@Named
@SessionScoped
public class MenuController implements Serializable {

    @EJB
    private MenuEJB menuEJB;

    @Inject
    private DishController dishController;

    List<Dish> dishes;

    String dateString;

    Map<Dish, Boolean> dishCheckMap;


    public void createDishHashMap() {
        dishCheckMap = new HashMap<>();
        dishes = dishController.getDishes();

        for (Dish dish : dishes) {
            dishCheckMap.put(dish, Boolean.FALSE);
        }
    }


    public String createMenu() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Set<Dish> checkedDishes = new HashSet<>();

        for (Map.Entry<Dish, Boolean> entry : dishCheckMap.entrySet()) {
            System.out.println(entry.getValue());
            if (entry.getValue()) {
                checkedDishes.add(entry.getKey());
            }
        }

        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
            menuEJB.createMenu(checkedDishes, date);

        } catch (Exception e) {
            return "menu.jsf";
        }

        return "home.jsf";
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public List<Dish> getDishes() {
        createDishHashMap();
        return dishes;
    }

    public Map<Dish, Boolean> getDishCheckMap() {
        return dishCheckMap;
    }


}
