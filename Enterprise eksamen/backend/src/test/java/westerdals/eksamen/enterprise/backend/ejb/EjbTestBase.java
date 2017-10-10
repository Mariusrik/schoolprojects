package westerdals.eksamen.enterprise.backend.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import westerdals.eksamen.enterprise.backend.entity.Dish;
import westerdals.eksamen.enterprise.backend.entity.Menu;
import westerdals.eksamen.enterprise.backend.util.DeleterEJB;

import javax.ejb.EJB;
import java.time.LocalDate;
import java.util.Set;


public abstract class EjbTestBase {

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "westerdals.eksamen.enterprise.backend")
                .addClass(DeleterEJB.class)
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    protected DishEJB dishEJB;

    @EJB
    protected MenuEJB menuEJB;


    @EJB
    private DeleterEJB deleterEJB;


    @Before
    @After
    public void emptyDatabase() {
        deleterEJB.deleteEntities(Menu.class);
        deleterEJB.deleteEntities(Dish.class);
    }


    protected Long createMenu(Set<Dish> dishes, LocalDate date) {

        return menuEJB.createMenu(dishes, date);
    }

    protected Long createDish(String name, String description) {

        return dishEJB.createDish(name, description);
    }


}
