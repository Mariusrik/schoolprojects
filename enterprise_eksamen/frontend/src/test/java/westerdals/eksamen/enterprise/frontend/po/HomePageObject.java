package westerdals.eksamen.enterprise.frontend.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by mariu on 08-Jun-17.
 */
public class HomePageObject extends PageObject {

    public HomePageObject(WebDriver driver) {
        super(driver);
    }


    public HomePageObject toStartingPage() {
        String context = "/my_cantina";
        driver.get("localhost:8080" + context + "/eksamen/home.jsf");
        waitForPageToLoad();

        return this;
    }


    public boolean isOnPage() {
        return driver.getTitle().equals("MyCantina Home Page");
    }

    public DishPageObject openDishPage() {
        WebElement link = driver.findElement(By.id("dishLink"));
        link.click();
        waitForPageToLoad();
        return new DishPageObject(driver);
    }

    public MenuPageObject openMenuPage() {
        WebElement link = driver.findElement(By.id("menuLink"));
        link.click();
        waitForPageToLoad();
        return new MenuPageObject(driver);
    }


    public String checkDefaultMenuCorrectDate() {
        WebElement link = driver.findElement(By.id("defaultMenuLinkForm:defaultMenu"));
        link.click();
        waitForPageToLoad();

        WebElement webElement = driver.findElement(By.id("currentMenuDate"));
        String dateText = webElement.getText();

        return dateText;
    }

    public String checkNextMenuDate() {
        WebElement link = driver.findElement(By.id("nextMenuLinkForm:nextMenu"));
        link.click();
        waitForPageToLoad();

        WebElement webElement = driver.findElement(By.id("currentMenuDate"));
        String dateText = webElement.getText();

        return dateText;
    }

    public String checkPreviousMenuDate() {
        WebElement link = driver.findElement(By.id("previousMenuLinkForm:previousMenu"));
        link.click();
        waitForPageToLoad();

        WebElement webElement = driver.findElement(By.id("currentMenuDate"));
        String dateText = webElement.getText();

        return dateText;
    }

    public List<WebElement> getAllMenus() {
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='dishTable']/tbody/tr/td[2]"));
        return elements;
    }
}
