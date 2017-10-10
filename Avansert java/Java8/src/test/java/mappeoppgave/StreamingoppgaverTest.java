package mappeoppgave;

import com.sun.javafx.collections.MappingChange;
import mappeoppgave.domene.Innsjoe;
import mappeoppgave.domene.Innsjoer;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class StreamingoppgaverTest {
    /**
     * Oppgave 1
     *
     * Bruk av filtering med Predicates
     *
     * i Finn alle innsjøer som har et navn som starter på C
     * ii Finn alle innsjøer som inneholder mer enn ett ord
     * iii Finn alle innsjøer som grenser til flere enn to land
     * iv Finn alle innsjøer som er i Europa, og som har et areal over 10000.0 km2, og som kun befinner seg i ett land
     * v Finn den første innsjøen som har et areal på over 5000.0 km2.
     */
    // I
    @Test
    public void skalFinneAlleSomStarterPaaC() throws Exception {
        List<Innsjoe> innsjoerSomBegynnerPaaC = Streamingoppgaver.begynnerPaaC();
        assertEquals(1, innsjoerSomBegynnerPaaC.size());
    }

    //II
    @Test
    public void skalFinneAlleSomInneholderMerEnnEtOrd() throws Exception {
        List<Innsjoe> innsjoerMerEnnEttOrd = Streamingoppgaver.merEnnEttOrd();
        assertEquals(6, innsjoerMerEnnEttOrd.size());
    }

    //III
    @Test
    public void skalFinneAlleGrenserMerennToLand() throws Exception {
        List<Innsjoe> innsjoerMerEnntoLand = Streamingoppgaver.merEnntoLand();
        assertEquals(4, innsjoerMerEnntoLand.size());
    }

    //IV
    @Test
    public void skalFinneAllemedArealovereTitusen() throws Exception {
        List<Innsjoe> innsjoerArealoverEthundretusen = Streamingoppgaver.arealovereTitusen();
        assertEquals(1, innsjoerArealoverEthundretusen.size());
    }

    //V
    @Test
    public void skalFinneAllemedForsteOverFemtusen() throws Exception {
        Innsjoe forsteInnsjoeArealStorsteOverFemtusen = Streamingoppgaver.arealForsteOverFemtusen();
        assertEquals("Caspian Sea", forsteInnsjoeArealStorsteOverFemtusen.navn());
    }


    /**
     * Oppgave 2
     *
     * Bruk av map med Function
     * i Returner en liste med navnene på alle innsjøene.
     * ii Returner en liste med navnene på alle innsjøene, med store bokstaver.
     * iii Returner en liste med innsjøer, hvor du endrer kontinent til Antarktis på alle.
     * iv Finn navnet på den første innsjøen som har et areal på over 500000.0 km2. Hvis det ikke finnes, skriv ut "Ingen". Hint: Se Optional.orElse.
     */
    //I
    @Test
    public void lageListemedNavn() throws Exception {
        List<String> listemedNavn = Streamingoppgaver.listemedNavn();

        String[] StringArray = {"Caspian Sea", "Superior", "Victoria", "Huron", "Michigan", "Tanganyika",
                "Baikal", "Great Bear Lake", "Malawi", "Great Slave Lake", "Erie", "Winnipeg", "Ontario", "Ladoga",
                "Balkhash", "Vostok", "Onega", "Titicaca", "Nicaragua", "Athabasca", "Taymyr", "Turkana",
                "Reindeer Lake", "Issyk-Kul", "Urmia", "Vänern", "Winnipegosis", "Albert", "Mweru", "Nettilling",
                "Sarygamysh Lake", "Nipigon", "Manitoba", "Great Salt Lake", "Saimaa", "Khanka"};
        List<String> forventetlistemedNavn = new ArrayList<>();
                forventetlistemedNavn.addAll(Arrays.asList(StringArray));

       assertThat(forventetlistemedNavn, is(listemedNavn));
    }

    //II
    @Test
    public void lageListemedNavnStoreBokstaver() throws Exception {
        List<String> listemedStoreNavn = Streamingoppgaver.listemedStoreNavn();

        String[] StringArray = {"CASPIAN SEA", "SUPERIOR", "VICTORIA", "HURON", "MICHIGAN", "TANGANYIKA",
                "BAIKAL", "GREAT BEAR LAKE", "MALAWI", "GREAT SLAVE LAKE", "ERIE", "WINNIPEG", "ONTARIO", "LADOGA",
                "BALKHASH", "VOSTOK", "ONEGA", "TITICACA", "NICARAGUA", "ATHABASCA", "TAYMYR", "TURKANA",
                "REINDEER LAKE", "ISSYK-KUL", "URMIA", "VÄNERN", "WINNIPEGOSIS", "ALBERT", "MWERU", "NETTILLING",
                "SARYGAMYSH LAKE", "NIPIGON", "MANITOBA", "GREAT SALT LAKE", "SAIMAA", "KHANKA"};
        List<String> forventetlistemedNavn = new ArrayList<>();
                forventetlistemedNavn.addAll(Arrays.asList(StringArray));

       assertThat(forventetlistemedNavn, is(listemedStoreNavn));
    }

    //III
    @Test
    public void lageListeEndreAltTilAntarktis() throws Exception {
        List<Innsjoe> listeEndreAlleTilAntarktis = Streamingoppgaver.listeEndreAlleTilAntarktis();

        for(Innsjoe innsjoe : listeEndreAlleTilAntarktis)
            assertEquals(innsjoe.kontinent(), "Antarctica");
        assertEquals(36, listeEndreAlleTilAntarktis.size());
    }

    //IIII
    @Test
    public void faaStorrelseOverFemhundretusen() throws Exception {
        String storrelseOverFemhundretusen = Streamingoppgaver.faaStorrelseOverFemhundretusen();

        assertEquals("Ingen" ,storrelseOverFemhundretusen);
    }



    /**
     * Oppgave 3
     *
     * Map, reduce og findAny
     * i Finn gjennomsnittlig areal på innsjøene.
     * ii Finn hvilken innsjø som har størst lengde.
     * iii Finn hvilken innsjø som har minst lengde.
     * iv Finn en innsjø som har en dybde på større enn 1/10 av lengden.
     * v Finn gjennomsnittlig antall land per innsjø.
     * vi Finn produktet av alle dybdene.
     */
    //I
    @Test
    public void gjennomsnittligArealPaaInnsjoene() throws Exception{
        Double gjennomsnittligAreal = Streamingoppgaver.gjennomsnittligArealPaaInnsjoene();
        assertTrue(30353.88888888889 == gjennomsnittligAreal);
    }

    //II
    @Test
    public void lengsteInnsjoeTest() throws Exception{
        Innsjoe lengsteInnsjoe = Streamingoppgaver.lengsteInnsjoe();
        assertEquals(Innsjoer.innsjoer.get(34) ,lengsteInnsjoe);
    }

    //III
    @Test
    public void kortesteInnsjoeTest() throws Exception{
        Innsjoe kortesteInnsjoe = Streamingoppgaver.kortesteInnsjoe();
        assertEquals(Innsjoer.innsjoer.get(35) ,kortesteInnsjoe);
    }

    //IV
    @Test
    public void innsjoDybdestorreEnnTidelAvLengdenTest() throws Exception{
        Innsjoe innsjoDybdestorreEnnTidelAvLengden = Streamingoppgaver.innsjoDybdestorreEnnTidelAvLengden();
        assertTrue((innsjoDybdestorreEnnTidelAvLengden.lengde() / 10) < innsjoDybdestorreEnnTidelAvLengden.maksDybde());
    }

    //V
    @Test
    public void gjennomsnittligAntallLandPerinnsjoTest() throws Exception{
        Double gjennomsnittligAntallLandPerinnsjo = Streamingoppgaver.gjennomsnittligAntallLandPerinnsjo();
        assertTrue(gjennomsnittligAntallLandPerinnsjo == 1.5833333333333333);
    }

    //VI
    @Test
    public void produktetAvDybdeneTest() throws Exception{
        Double produktetAvDybdene = Streamingoppgaver.produktetAvDybdene();
        assertTrue(produktetAvDybdene == 1.0915134706186265E79);
    }


    /**
     * Oppgave 4
     *
     * Avanserte Collectors
     *
     * i Grupper innsjøene per kontinent i en Map<String, List<Innsjoe>>
     * ii Finn ut hvor mange innsjøer hvert kontinent har.
     * iii Bruk Collectors.joining(String) til å få en liste med navnene på innsjøene, separert med tegnet "|"
     * iv Bruk Collectors.averagingDouble(int) til å finne gjennomsnittlig areal på innsjøene.
     * v Bruk Collectors.partitioningBy(Predicate) til å returnere et map med to lister, én med innsjøer med dybde over 500 meter, og én med de under.
     */
    //I
    @Test
    public void innsjoPerKontinentTest() throws Exception{
        Map<String, List<Innsjoe>> innsjoPerKontinent= Streamingoppgaver.innsjoPerKontinent();

        for(int i = 0; i < innsjoPerKontinent.get("Africa").size(); i++)
            assertEquals(innsjoPerKontinent.get("Africa").get(i).kontinent(), "Africa");

        for(int i = 0; i < innsjoPerKontinent.get("North America").size(); i++)
            assertEquals(innsjoPerKontinent.get("North America").get(i).kontinent(), "North America");

        for(int i = 0; i < innsjoPerKontinent.get("Asia").size(); i++)
            assertEquals(innsjoPerKontinent.get("Asia").get(i).kontinent(), "Asia");
    }

    //II
    @Test
    public void hvorMangeInnsjoerPerKontinentTest() throws Exception{
        Map<String, Long> hvorMangeInnsjoerPerKontinent = Streamingoppgaver.hvorMangeInnsjoerPerKontinent();
        assertTrue(1 == hvorMangeInnsjoerPerKontinent.get("South America"));
        assertTrue(8 == hvorMangeInnsjoerPerKontinent.get("Asia"));
        assertTrue(4 == hvorMangeInnsjoerPerKontinent.get("Europe"));
        assertTrue(6 == hvorMangeInnsjoerPerKontinent.get("Africa"));
        assertTrue(1 == hvorMangeInnsjoerPerKontinent.get("Antarctica"));
        assertTrue(16 == hvorMangeInnsjoerPerKontinent.get("North America"));
    }

    //III
    @Test
    public void listeMedNavnTest() throws Exception{
        String listeMedNavn = Streamingoppgaver.listeMedNavn();

        assertEquals("Caspian Sea|Superior|Victoria|Huron|Michigan|", listeMedNavn.substring(0,45));
    }

    //IV
    @Test
    public void gjennomsnittligAralTest() throws Exception{
        double gjennomsnittligAral = Streamingoppgaver.gjennomsnittligAral();
        double sum = 0;
        int i = 0;
        for(Innsjoe innsjoe : Innsjoer.innsjoer){
            sum += innsjoe.areal();
            i++;
        }
        sum = (sum/i);
        assertTrue(sum == gjennomsnittligAral);
    }

    //V
    @Test
    public void toListerTest() throws Exception{
        Map<Boolean, List<Innsjoe>> toLister = Streamingoppgaver.toLister();

        assertEquals("Caspian Sea" ,toLister.get(true).get(0).navn());
        assertEquals("Superior" ,toLister.get(false).get(0).navn());
    }


    /**
     * Oppgave5
     * Flatmap
     * i Returner en liste med alle landene som er representert i lista.
     * ii Returner en liste med alle landene som er representert i lista, uten duplikater.
     * iii Returner en liste med alle landene som er representert i lista, og tell antall ganger hvert land er representert.
     */
    //I
    @Test
    public void listeMedLandTest() throws Exception{
        List<String> listeMedLand = Streamingoppgaver.listeMedLand();
        String alleLand = "";
        String alleLandActual = "";
        for(int i = 0; i < Innsjoer.innsjoer.size(); i++){
            for(int j = 0; j < Innsjoer.innsjoer.get(i).land().size(); j++){
                alleLand += Innsjoer.innsjoer.get(i).land().get(j);
            }
        }
        for(String land : listeMedLand)
            alleLandActual += land;

        assertEquals(alleLand, alleLandActual);
    }

    //II
    @Test
    public void listeMedUnikeLandTest() throws Exception{
        List<String> listeMedUnikeLand = Streamingoppgaver.listeMedUnikeLand();

        HashSet<String> hashset = new HashSet<>();
        for(int i = 0; i < Innsjoer.innsjoer.size(); i++){
            for(int j = 0; j < Innsjoer.innsjoer.get(i).land().size(); j++){
                hashset.add(Innsjoer.innsjoer.get(i).land().get(j));
            }
        }
        String alleUnikeLandTest = "";
        for(String land : hashset){
            alleUnikeLandTest += land;
        }

        String alleLandActual = "";
        for(String land : listeMedUnikeLand)
            alleLandActual += land;

        //sjekker både om de har like mange tegn, og om det er like mange land.
        assertTrue(listeMedUnikeLand.size() == hashset.size());
        assertTrue(alleUnikeLandTest.length() == alleLandActual.length());
    }

    //III
    @Test
    public void alleLandmedAntallTest() throws Exception{
        Map<String, Long> alleLandmedAntall = Streamingoppgaver.alleLandmedAntall();

        assertTrue(alleLandmedAntall.get("Canada") == 13);
        assertTrue(alleLandmedAntall.get("United States") == 6);
    }
}