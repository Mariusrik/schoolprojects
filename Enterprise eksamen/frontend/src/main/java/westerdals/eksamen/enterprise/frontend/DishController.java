package westerdals.eksamen.enterprise.frontend;

import westerdals.eksamen.enterprise.backend.ejb.DishEJB;
import westerdals.eksamen.enterprise.backend.entity.Dish;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by mariu on 08-Jun-17.
 */

@Named
@SessionScoped
public class DishController implements Serializable {

    @EJB
    private DishEJB dishEJB;


    private String formName;
    private String formDescription;

    public List<Dish> getDishes() {

        List<Dish> dishes = dishEJB.getAllDishes();
        return dishes;
    }

    public void createNewDish() {

        try {
            dishEJB.createDish(formName, formDescription);
        } catch (Exception e) {
        }
    }


    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormDescription() {
        return formDescription;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }


}
