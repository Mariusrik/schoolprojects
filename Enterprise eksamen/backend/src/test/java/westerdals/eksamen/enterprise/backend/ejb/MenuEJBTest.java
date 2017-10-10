package westerdals.eksamen.enterprise.backend.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import westerdals.eksamen.enterprise.backend.entity.Dish;
import westerdals.eksamen.enterprise.backend.entity.Menu;

import javax.ejb.EJBException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by mariu on 07-Jun-17.
 */
@RunWith(Arquillian.class)
public class MenuEJBTest extends EjbTestBase {

    /***
     * #######################################################################################
     * Tests mentioned in the exam
     */

    @Test(expected = EJBException.class)
    public void testCreateMenuWithNoDish() {
        menuEJB.createMenu(new HashSet<>(), LocalDate.now());
    }

    @Test(expected = EJBException.class)
    public void testCreateMenuNullDish() {
        menuEJB.createMenu(null, LocalDate.now());
    }


    /**
     * Tests if current menu is retured fron getCurrentMenu()
     */
    @Test
    public void testGetCurrentMenu() {

        Long id = createDish("dish1", "description1");
        Dish dish = dishEJB.getDish(id);
        Set<Dish> dishlist = new HashSet<>();
        dishlist.add(dish);
        LocalDate date = LocalDate.now();

        createMenu(dishlist, LocalDate.of(2017, 3, 18));
        Long todayMenuId = createMenu(dishlist, date);


        Menu menu = menuEJB.getCurrentMenu();

        assertEquals(menu.getId(), todayMenuId);
    }

    @Test
    public void testGetAbsentPreviousMenu() {
        LocalDate date = LocalDate.now();

        assertNull(menuEJB.getPreviousMenu(date));
    }

    @Test
    public void testGetAbsentNextMenu() {
        LocalDate date = LocalDate.now();

        assertNull(menuEJB.getNextMenu(date));
    }


    @Test
    public void testGetPreviousMenu() {

        Long id = createDish("dish1", "description1");
        Dish dish = dishEJB.getDish(id);
        Set<Dish> dishlist = new HashSet<>();
        dishlist.add(dish);

        LocalDate date = LocalDate.now();

        Long todayMenuId = createMenu(dishlist, date);
        Long yesterdayMenuId = createMenu(dishlist, date.minusDays(1));
        Long dayBeforeYesterdayMenuId = createMenu(dishlist, date.minusDays(2));

        Menu menu = menuEJB.getPreviousMenu(date);

        assertEquals(menu.getId(), yesterdayMenuId);
    }


    // TODO: refacor into 3 ekstra private methods if time.
    @Test
    public void testThreeMenus() {
        Long id = createDish("dish", "description");
        Dish dish = dishEJB.getDish(id);
        Set<Dish> dishlist = new HashSet<>();
        dishlist.add(dish);

        LocalDate todayDate = LocalDate.now();
        LocalDate yesterdayDate = todayDate.minusDays(1);
        LocalDate tomorrowDate = todayDate.plusDays(1);

        Long todayMenuId = createMenu(dishlist, todayDate);
        Long yesterdayMenuId = createMenu(dishlist, yesterdayDate);
        Long tomorrowMenuId = createMenu(dishlist, tomorrowDate);

        //verify that today has tomorrow as next, and yesterday as previous
        assertEquals(tomorrowMenuId, menuEJB.getNextMenu(todayDate).getId());
        assertEquals(yesterdayMenuId, menuEJB.getPreviousMenu(todayDate).getId());

        //verify that tomorrow has no next, and today as previous
        assertNull(menuEJB.getNextMenu(tomorrowDate));
        assertEquals(todayMenuId, menuEJB.getPreviousMenu(tomorrowDate).getId());

        //verify that yesterday has no previous, and today as next
        assertNull(menuEJB.getPreviousMenu(yesterdayDate));
        assertEquals(todayMenuId, menuEJB.getNextMenu(yesterdayDate).getId());
    }


    /***
     * #######################################################################################
     * Tests mentioned in the exam
     */

    /*
     * checks if getCurrentMenu returns next menu if today is not present
     */
    @Test
    public void testGetCurrentMenuNext() {

        Long id = createDish("dish1", "description1");
        Dish dish = dishEJB.getDish(id);
        Set<Dish> dishlist = new HashSet<>();
        dishlist.add(dish);
        LocalDate date = LocalDate.now();

        Long tomorrowMenuId = createMenu(dishlist, date.plusDays(1));
        Long theDayAfterTomorrowMenuId = createMenu(dishlist, date.plusDays(2));

        Menu menu = menuEJB.getCurrentMenu();

        assertEquals(menu.getId(), tomorrowMenuId);
    }


    /*
    * checks if getCurrentMenu returns previous menu if today is not present and there is no after that either
    */
    @Test
    public void testGetCurrentMenuPrevious() {

        Long id = createDish("dish1", "description1");
        Dish dish = dishEJB.getDish(id);
        Set<Dish> dishlist = new HashSet<>();
        dishlist.add(dish);
        LocalDate date = LocalDate.now();

        Long yesterdayMenuId = createMenu(dishlist, date.minusDays(1));
        Long dayAfteryesterdayMenuId = createMenu(dishlist, date.minusDays(2));

        Menu menu = menuEJB.getCurrentMenu();

        assertEquals(menu.getId(), yesterdayMenuId);
    }

    @Test
    public void testGetNextMenu() {

        Long id = createDish("dish1", "description1");
        Dish dish = dishEJB.getDish(id);
        Set<Dish> dishlist = new HashSet<>();
        dishlist.add(dish);

        LocalDate date = LocalDate.now();

        Long todayMenuId = createMenu(dishlist, date);
        Long tomorrowMenuId = createMenu(dishlist, date.plusDays(1));
        Long dayafterTomorrowMenuId = createMenu(dishlist, date.plusDays(2));

        Menu menu = menuEJB.getNextMenu(date);

        assertEquals(menu.getId(), tomorrowMenuId);
    }


    @Test(expected = EJBException.class)
    public void testCreateMenusSameDate() {
        Long id = createDish("dish", "description");
        Dish dish = dishEJB.getDish(id);
        Set<Dish> dishlist = new HashSet<>();
        dishlist.add(dish);

        LocalDate todayDate = LocalDate.now();

        createMenu(dishlist, todayDate);
        createMenu(dishlist, todayDate);
    }

}
