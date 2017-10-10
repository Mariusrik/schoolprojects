import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Dette er ment som en enkel demonstrasjon av ORM
 * Exeption handling er heller ikke gjort mye ut av ettersom dette ikke er produksjonskode.
 * Går ut ifra at det er nok for oppgaven å bare vise at man får til CRUD med ORMlite og at hoved-vektingen går på innleveringene
 *
 * Created by rikmar15 on 07.11.2016.
 */
@DatabaseTable(tableName = "continent")
public class Continent {

    public static final String ID_FIELD_NAME = "id";
    public static final String NAME_FIELD_NAME = "name";
    public static final String NCOUNTRIES_FIELD_NAME = "ncountries";
    public static final String NCITIES_FIELD_NAME = "ncities";
    public static final String POPULATION_FIELD_NAME = "population";

    @DatabaseField(columnName = ID_FIELD_NAME, canBeNull = false, generatedId = true)
    private int id;

    @DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
    private String name;



    @DatabaseField(columnName = NCOUNTRIES_FIELD_NAME, canBeNull = false)
    private Long ncountries;

    @DatabaseField(columnName = NCITIES_FIELD_NAME, canBeNull = false)
    private Long ncities;

    @DatabaseField(columnName = POPULATION_FIELD_NAME, canBeNull = false)
    private Long population;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Long getNcountries() {
        return ncountries;
    }

    public void setNcountries(Long ncountries) {
        this.ncountries = ncountries;
    }

    public Long getNcities() {
        return ncities;
    }

    public void setNcities(Long ncities) {
        this.ncities = ncities;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Continent() {
        //empty on purpose
    }


    public Continent(int id, String name, Long ncountries, Long ncities, Long population) {
        this.id = id;
        this.name = name;
        this.ncountries = ncountries;
        this.ncities = ncities;
        this.population = population;
    }

    public Continent(String name, Long ncountries, Long ncities, Long population) {
        this.name = name;
        this.ncountries = ncountries;
        this.ncities = ncities;
        this.population = population;
    }


    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ncountries=" + ncountries +
                ", ncities=" + ncities +
                ", population=" + population +
                '}';
    }

}