import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Dette er ment som en enkel demonstrasjon av ORM
 * Exeption handling er heller ikke gjort mye ut av ettersom dette ikke er produksjonskode.
 * Går ut ifra at det er nok for oppgaven å bare vise at man får til CRUD med ORMlite og at hoved-vektingen går på innleveringene
 *
 * Created by rikmar15 on 07.11.2016.
 */
@DatabaseTable(tableName = "city")
public class City {

    public static final String ID_FIELD_NAME = "id";
    public static final String NAME_FIELD_NAME = "name";
    public static final String COUNTRYCODE_FIELD_NAME = "countrycode";
    public static final String DISTRICT_FIELD_NAME = "district";
    public static final String POPULATION_FIELD_NAME = "population";


    @DatabaseField(columnName = ID_FIELD_NAME, canBeNull = true, id = true)
    private Integer id;

    @DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
    private String name;
/*
    @DatabaseField(columnName = COUNTRYCODE_FIELD_NAME, canBeNull = false)
    private String countryCode;
*/
    @DatabaseField(columnName = DISTRICT_FIELD_NAME, canBeNull = false)
    private String district;

    @DatabaseField(columnName = POPULATION_FIELD_NAME, canBeNull = false)
    private int population;

    @DatabaseField(canBeNull = false, columnName = COUNTRYCODE_FIELD_NAME, foreign = true)
    private Country country;


    public City() {
        //empty on purpose
    }


    public City(int id, String name, Country country, String district, int population) {
        this.name = name;
        this.id = id;
        this.country = country;
        this.district = district;
        this.population = population;
    }



    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public String getDistrict() {
        return district;
    }

    public int getPopulation() {
        return population;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
    @Override
    public String toString(){
        return "Id:" + getId() + ". City Name:" + getName() + ". Country Code:"+ getCountry().getCode() + ". District:" + getDistrict() + ". Population:" + getPopulation();
    }
}
