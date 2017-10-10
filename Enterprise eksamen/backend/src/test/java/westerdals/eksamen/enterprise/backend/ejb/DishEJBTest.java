package westerdals.eksamen.enterprise.backend.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import westerdals.eksamen.enterprise.backend.entity.Dish;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mariu on 07-Jun-17.
 */
@RunWith(Arquillian.class)
public class DishEJBTest extends EjbTestBase {


    /***
     * #######################################################################################
     * Tests mentioned in the exam
     */


    @Test
    public void testCreateDish() {
        Long id = createDish("dish1", "description1");
        Dish dish = dishEJB.getDish(id);

        assertEquals("dish1", dish.getName());
    }

    @Test
    public void testCreateTwoDishes() {
        Long dishId1 = createDish("dish1", "description1");
        Long dishId2 = createDish("dish2", "description2");

        Dish dish = dishEJB.getDish(dishId1);
        Dish dish2 = dishEJB.getDish(dishId2);

        assertEquals("dish1", dish.getName());
        assertEquals("dish2", dish2.getName());
    }


    /***
     * ##########################################################################################
     * Tests mentioned in the exam
     */


    @Test
    public void testGetAllDishes() {

        Long id = createDish("dish1", "description1");
        Long id2 = createDish("dish2", "description2");

        List<Dish> list = dishEJB.getAllDishes();

        assertEquals(2, list.size());

        assertEquals(id, list.get(0).getId());
        assertEquals(id2, list.get(1).getId());
    }
}
