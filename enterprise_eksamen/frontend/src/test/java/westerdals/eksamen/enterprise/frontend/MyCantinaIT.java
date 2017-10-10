package westerdals.eksamen.enterprise.frontend;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import westerdals.eksamen.enterprise.frontend.po.DishPageObject;
import westerdals.eksamen.enterprise.frontend.po.MenuPageObject;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mariu on 08-Jun-17.
 */
public class MyCantinaIT extends WebTestBase {

    @Test
    public void testHomePage() {
        home.toStartingPage();
        assertTrue(home.isOnPage());
    }

    @Test
    public void testCreateDish() {
        String name = getUniqueName();
        DishPageObject dishObject = home.openDishPage();
        assertTrue(dishObject.isOnPage());

        dishObject.getNumberOfDishes();

        assertEquals(0, dishObject.getNumberOfDishesContainingName(name));

        dishObject.createDish(name, "description1");
        dishObject.createDish(name, "description2");
        dishObject.createDish(name, "description3");

        assertEquals(3, dishObject.getNumberOfDishesContainingName(name));

    }


    @Test
    public void testMenu() {

        String name = getUniqueName();

        DishPageObject dishObject = home.openDishPage();
        assertTrue(dishObject.isOnPage());

        assertEquals(0, dishObject.getNumberOfDishesContainingName(name));

        //create 3 new dishes
        dishObject.createDish(name, "description1");
        dishObject.createDish(name, "description2");
        dishObject.createDish(name, "description3");
        assertEquals(3, dishObject.getNumberOfDishesContainingName(name));

        //go on menu creation page
        dishObject.navigateHome();
        assertTrue(home.isOnPage());
        MenuPageObject menuPageObject = home.openMenuPage();
        assertTrue(menuPageObject.isOnPage());

        //verify that checkboxes are present
        assertTrue(menuPageObject.isCheckboxesVisible());


        //create menu for today
        String dateString = LocalDate.now().toString();
        //making sure i used the new added dishes
        List<Integer> positionList = menuPageObject.getPositionsOfAddedDishes(name);
        menuPageObject.createMenu(positionList.get(0), positionList.get(1), dateString);

        //id date already submitted go to home page manually.
        if (!home.isOnPage()) {
            menuPageObject.navigateHome();
        }

        //verify that the date of the displayed menu is correct (ie today)
        assertEquals("Menu for " + dateString, home.checkDefaultMenuCorrectDate());


        //verify that the 2 selected dishes are displayed in the menu, and only those 2.
        List<WebElement> allMenus = home.getAllMenus();
        boolean bool1 = false;
        boolean bool2 = false;
        for (int i = 0; i < allMenus.size(); i++) {
            if (allMenus.get(i).getText().equals("description3")) {
                bool1 = true;
            }
            if (allMenus.get(i).getText().equals("description2")) {
                bool2 = true;
            }
        }
        assertTrue(bool1 && bool2);
        assertEquals(2, allMenus.size());
    }

    @Test
    public void testDifferentDates() {
        LocalDate today = LocalDate.now();
        String todayDateString = today.toString();
        String tomorrowDateString = LocalDate.now().plusDays(1).toString();
        String yesterdaydateString = LocalDate.now().minusDays(1).toString();

        DishPageObject dishObject = home.openDishPage();
        //create one dish to make certain there is one in the DB, but i dont care which i get later.
        dishObject.createDish("name", "description");
        dishObject.navigateHome();
        assertTrue(home.isOnPage());

        MenuPageObject menuPageObject = home.openMenuPage();

        //if date is already created it wont redirect to home page, so make it as easy as possible i force it to go to back to home page anyways.
        //create 3 menus: one for today, one for tomorrow, and one for yesterday
        menuPageObject.createMenu(0, 1, todayDateString);
        if (!home.isOnPage()) {
            menuPageObject.navigateHome();
        }
        menuPageObject = home.openMenuPage();
        menuPageObject.createMenu(0, 1, yesterdaydateString);
        if (!home.isOnPage()) {
            menuPageObject.navigateHome();
        }
        menuPageObject = home.openMenuPage();
        menuPageObject.createMenu(0, 1, tomorrowDateString);
        if (!home.isOnPage()) {
            menuPageObject.navigateHome();
        }


        //on home page click show default to visualize the menu of today, and verify it
        assertEquals("Menu for " + todayDateString, home.checkDefaultMenuCorrectDate());

        //click next, and verify that the menu of tomorrow is displayed
        assertEquals("Menu for " + tomorrowDateString, home.checkNextMenuDate());

        // click previous, and verify that the menu of today is displayed
        assertEquals("Menu for " + todayDateString, home.checkPreviousMenuDate());

        //click previous, and verify that the menu of yesterday is displayed
        assertEquals("Menu for " + yesterdaydateString, home.checkPreviousMenuDate());

    }
}
