import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

/**
 * Dette er ment som en enkel demonstrasjon av ORM
 * Exeption handling er heller ikke gjort mye ut av ettersom dette ikke er produksjonskode.
 * Går ut ifra at det er nok for oppgaven å bare vise at man får til CRUD med ORMlite og at hoved-vektingen går på innleveringene
 *
 * Created by rikmar15 on 07.11.2016.
 */
public class CRUDExample {

    Connection connection = new Connection();
    Dao<City, Integer> cityDao;
    Dao<Continent, Integer> continentDao;
    Dao<Country, String> countryDao;
    ConnectionSource con;


    public CRUDExample() throws SQLException{
        con = connection.createConnection();
        cityDao = DaoManager.createDao(con, City.class);
        continentDao = DaoManager.createDao(con, Continent.class);
        countryDao = DaoManager.createDao(con, Country.class);
    }

    public void showcase() throws SQLException {

        // Cleanup
        TableUtils.dropTable(this.continentDao, true);

        System.out.print("Creating Continent table: ");
        System.out.flush();
        TableUtils.createTable(this.continentDao);
        System.out.println("OK");

        System.out.println("Building continent table:");

        List<Country> continents = this.countryDao.queryBuilder()
                .groupBy(Country.CONTINENT_FIELD_NAME)
                .selectColumns(Country.CONTINENT_FIELD_NAME)
                .query();

        // Order it alphabetically
        continents.sort(Comparator.comparing(Country::getContinent));

        for (Country cont : continents ) {
            System.out.format("  - %s: ", cont.getContinent());
            System.out.flush();



            // The information needed for each continent will be fetched in two different manners. First by using
            // ormlites' ForeignCollection relation and then by using a join. This is done to show of multiple ways
            // of fetching information with ORM
            List<Country> countries = this.countryDao.queryForEq(Country.CONTINENT_FIELD_NAME, cont.getContinent());
            long numCountries = countries.size();


            long numCities = 0;
            for (Country ccon : countries) {
                // Here i use ForeignCollections. This allows for relationships in an object oriented way,
                // without thinking about the underlaying SQL structure.
                numCities += ccon.cities.size();
            }

            // This could have been done better, but the main idea here is to show of how to do a subquery.
            QueryBuilder<Country, String> qbcnt = this.countryDao.queryBuilder().selectColumns(Country.CODE_FIELD_NAME);
            qbcnt.where().eq(Country.CONTINENT_FIELD_NAME, cont.getContinent());

            List<City> ccts = this.cityDao.queryBuilder().where().in(City.COUNTRYCODE_FIELD_NAME, qbcnt).query();
            long population = 0;
            for (City ccc : ccts) {
                population += ccc.getPopulation();
            }

            Continent nCont = new Continent(cont.getContinent(), numCountries, numCities, population);
            this.continentDao.createOrUpdate(nCont);
            System.out.println("OK");
        }

        System.out.print("Dropping continent table: ");
        System.out.flush();
        TableUtils.dropTable(this.continentDao, false);
        System.out.println("OK");
    }


    public ConnectionSource getConnection(){
        return con;
    }
}
