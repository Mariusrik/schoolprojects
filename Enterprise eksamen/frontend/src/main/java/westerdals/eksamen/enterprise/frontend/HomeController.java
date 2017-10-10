package westerdals.eksamen.enterprise.frontend;

import westerdals.eksamen.enterprise.backend.ejb.MenuEJB;
import westerdals.eksamen.enterprise.backend.entity.Menu;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by mariu on 07-Jun-17.
 */

@Named
@SessionScoped
public class HomeController implements Serializable {

    @EJB
    private MenuEJB menuEJB;

    Menu menu;

    public Menu getCurrentMenu() {
        if (menu == null) {
            menu = menuEJB.getCurrentMenu();
            return menu;
        } else
            return menu;
    }


    public String getDefaultMenu() {
        menu = menuEJB.getCurrentMenu();
        return "/eksamen/home.jsf";
    }


    public Menu getNextMenu(LocalDate date) {
        return menuEJB.getNextMenu(date);
    }


    public String setNextMenu(LocalDate date) {
        Menu nextMenu = menuEJB.getNextMenu(date);
        if (nextMenu != null)
            menu = nextMenu;

        return "/eksamen/home.jsf";
    }

    public Menu getPreviousMenu(LocalDate date) {
        return menuEJB.getPreviousMenu(date);
    }

    public String setPrevousMenu(LocalDate date) {
        Menu previousMenu = menuEJB.getPreviousMenu(date);
        if (previousMenu != null)
            menu = previousMenu;

        return "/eksamen/home.jsf";
    }


    public String getFormattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
