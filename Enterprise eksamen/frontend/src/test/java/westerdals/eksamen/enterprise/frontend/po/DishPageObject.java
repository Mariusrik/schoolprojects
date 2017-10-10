package westerdals.eksamen.enterprise.frontend.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by mariu on 08-Jun-17.
 */
public class DishPageObject extends PageObject {

    public DishPageObject(WebDriver driver) {
        super(driver);
    }

    public void navigateHome() {
        WebElement link = driver.findElement(By.id("homeLink"));
        link.click();
        waitForPageToLoad();
    }


    public boolean isOnPage() {
        return driver.getTitle().equals("Dishes");
    }


    public void createDish(String name, String description) {
        setText("createForm:name", name);
        setText("createForm:description", description);
        WebElement button = driver.findElement(By.id("createForm:createButton"));
        button.click();
        waitForPageToLoad();
    }

    public int getNumberOfDishesContainingName(String name) {
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='dishTable']/tbody/tr/td[contains(text(),'" + name + "')]"));
        return elements.size();
    }

    public int getNumberOfDishes() {
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='dishTable']//tbody//tr"));
        return elements.size();
    }


}
