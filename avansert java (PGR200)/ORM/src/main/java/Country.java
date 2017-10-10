import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Dette er ment som en enkel demonstrasjon av ORM
 * Exeption handling er heller ikke gjort mye ut av ettersom dette ikke er produksjonskode.
 * Går ut ifra at det er nok for oppgaven å bare vise at man får til CRUD med ORMlite og at hoved-vektingen går på innleveringene
 *
 * Created by rikmar15 on 07.11.2016.
 */
@DatabaseTable(tableName = "country")
public class Country {


    public static final String NAME_FIELD_NAME = "name";
    public static final String CODE_FIELD_NAME = "code" ;
    public static final String CONTINENT_FIELD_NAME = "continent";
    public static final String POPULATION_FIELD_NAME = "population";

    @ForeignCollectionField(eager = false)
    ForeignCollection<City> cities;


    @DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = CODE_FIELD_NAME, canBeNull = false, id = true)
    private String code;

    @DatabaseField(columnName = CONTINENT_FIELD_NAME, canBeNull = false)
    private String continent;

    @DatabaseField(columnName = POPULATION_FIELD_NAME, canBeNull = false)
    private int population;


    public Country() {
        //empty on purpose
    }


    public Country(String name, String code, String continent, int population) {
        this.name = name;
        this.code = code;
        this.continent = continent;
        this.population = population;
    }



    public String getName() {
        return name;
    }


    public String getCode() {
        return code;
    }

    public String getContinent() {
        return continent;
    }

    public int getPopulation() {
        return population;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
    @Override
    public String toString(){
        return  "Country Name:" + getName() + ". Country Code:"+ getCode() + ". District:" + getContinent() + ". Population:" + getPopulation();
    }
}
