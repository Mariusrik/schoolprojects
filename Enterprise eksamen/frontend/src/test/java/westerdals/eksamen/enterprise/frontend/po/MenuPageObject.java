package westerdals.eksamen.enterprise.frontend.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariu on 08-Jun-17.
 */
public class MenuPageObject extends PageObject {

    public MenuPageObject(WebDriver driver) {
        super(driver);
    }

    public void navigateHome() {
        WebElement link = driver.findElement(By.id("homeLink"));
        link.click();
        waitForPageToLoad();
    }

    public boolean isOnPage() {
        return driver.getTitle().equals("Menu");
    }


    public boolean isCheckboxesVisible() {
        List<WebElement> elements = driver.findElements(
                By.xpath("//table[@id='dishSelectForm:selectDishTable']/tbody/tr/td/input[@type='checkbox']"));

        return !elements.isEmpty();
    }


    public void createMenu(int position, int position2, String date) {
        WebElement checkbox1 = driver.findElement(By.id("dishSelectForm:selectDishTable:" + position + ":include"));
        WebElement checkbox2 = driver.findElement(By.id("dishSelectForm:selectDishTable:" + position2 + ":include"));
        checkbox1.click();
        checkbox2.click();

        setText("dishSelectForm:date", date);

        WebElement button = driver.findElement(By.id("dishSelectForm:createButton"));
        button.click();

        waitForPageToLoad();
    }

    public int getNumberOfDishes() {
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='dishSelectForm:selectDishTable']//tbody//tr"));
        return elements.size();
    }

    public List<Integer> getPositionsOfAddedDishes(String name) {
        List<Integer> positionList = new ArrayList<>();
        for (int i = 1; i <= getNumberOfDishes(); i++) {
            String rowValueName = driver.findElement(By.xpath("//table[@id='dishSelectForm:selectDishTable']/tbody/tr[" + i + "]/td[1]")).getText();
            System.out.println(rowValueName);
            System.out.println("######");
            if (rowValueName.equals(name)) {
                //elements.add(driver.findElement(By.xpath("//table[@id='dishSelectForm:selectDishTable']/tbody/tr[" + i + "]/td[2]")));
                positionList.add(i);
            }
        }
        return positionList;
    }

}
