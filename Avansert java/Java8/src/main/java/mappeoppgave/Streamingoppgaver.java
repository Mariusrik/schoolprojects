package mappeoppgave;

import mappeoppgave.domene.Innsjoe;
import mappeoppgave.domene.Innsjoer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

public class Streamingoppgaver {

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
    //I
    public static List<Innsjoe> begynnerPaaC() {
        List<Innsjoe> foundinnsjoer = Innsjoer.innsjoer.stream().filter(e -> e.navn().toLowerCase().startsWith("c")).collect(Collectors.toList());
        return foundinnsjoer;
    }

    //II
    public static List<Innsjoe> merEnnEttOrd() {
        List<Innsjoe> foundinnsjoer = Innsjoer.innsjoer.stream().filter(e -> e.navn().trim().contains(" ")).collect(Collectors.toList());
        return foundinnsjoer;
    }

    //III
    public static List<Innsjoe> merEnntoLand() {
        List<Innsjoe> foundinnsjoer = Innsjoer.innsjoer.stream().filter(e -> e.land().size() > 2).collect(Collectors.toList());
        return foundinnsjoer;
    }

    //IV
    public static List<Innsjoe> arealovereTitusen() {
        List<Innsjoe> foundinnsjoer = Innsjoer.innsjoer.stream()
                .filter(e -> e.areal() > 10000)
                .filter(e -> e.kontinent().equals("Europe"))
                .filter(e -> e.land().size() == 1)
                .collect(Collectors.toList());
        return foundinnsjoer;
    }

    //V
    public static Innsjoe arealForsteOverFemtusen() {
        Innsjoe foundinnsjoe = Innsjoer.innsjoer.stream().filter(e -> e.areal() > 5000).findFirst().get();
        return foundinnsjoe;
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
    public static List<String> listemedNavn() {
        List<String> foundinnsjoer = Innsjoer.innsjoer.stream().map(Innsjoe::navn).collect(Collectors.toList());
        return foundinnsjoer;
    }
    //II
    public static List<String> listemedStoreNavn() {
        List<String> foundinnsjoer = Innsjoer.innsjoer.stream().map(e -> e.navn().toUpperCase()).collect(Collectors.toList());
        return foundinnsjoer;
    }

    //III
    public static List<Innsjoe> listeEndreAlleTilAntarktis() {
        List<Innsjoe> foundinnsjoer = Innsjoer.innsjoer.stream().map(i -> new Innsjoe(i.navn(), i.land(), "Antarctica", i.areal(), i.lengde(), i.maksDybde())).collect(Collectors.toList());
        return foundinnsjoer;
    }

    //IV
    public static String faaStorrelseOverFemhundretusen() {
        String foundinnsjoer = Innsjoer.innsjoer.stream().filter(i -> i.areal() > 500000).map(i -> i.navn()).findFirst().orElse("Ingen");
        return foundinnsjoer;
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
    public static Double gjennomsnittligArealPaaInnsjoene(){
        Double gjennomsnittligInnsjoer = Innsjoer.innsjoer.stream().mapToDouble(Innsjoe::areal).average().getAsDouble();
        return  gjennomsnittligInnsjoer;
    }

    //II
    public static Innsjoe lengsteInnsjoe(){
        Innsjoe lengsteInnsjoe = Innsjoer.innsjoer.stream().max(Comparator.comparing(Innsjoe::lengde)).get();
        return  lengsteInnsjoe;
    }

    //III
    public static Innsjoe kortesteInnsjoe(){
        Innsjoe kortesteInnsjoe = Innsjoer.innsjoer.stream().min(Comparator.comparing(Innsjoe::lengde)).get();
        return  kortesteInnsjoe;
    }

    //IV
    //
    public static Innsjoe innsjoDybdestorreEnnTidelAvLengden(){
        Innsjoe kortesteInnsjoe = Innsjoer.innsjoer.stream().filter(innsjoe -> (innsjoe.lengde()/10) < innsjoe.maksDybde()).findAny().get();
        return  kortesteInnsjoe;
    }

    //V
    public static Double gjennomsnittligAntallLandPerinnsjo(){
        Double gjennomsnittligAntallLandPerinnsjo = Innsjoer.innsjoer.stream().mapToDouble(Innsjoe -> Innsjoe.land().size()).average().getAsDouble();
        return  gjennomsnittligAntallLandPerinnsjo;
    }

    //VI
    public static Double produktetAvDybdene(){
        Double produktetAvDybdene = Innsjoer.innsjoer.stream().mapToDouble(Innsjoe::maksDybde).reduce(1, (a, b) -> a*b);
        return  produktetAvDybdene;
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
    public static Map<String, List<Innsjoe>> innsjoPerKontinent(){
        Map<String, List<Innsjoe>> innsjoPerKontinent= Innsjoer.innsjoer.stream().collect(Collectors.groupingBy(Innsjoe::kontinent));
        return  innsjoPerKontinent;
    }

    //II
    public static Map<String, Long>  hvorMangeInnsjoerPerKontinent(){
        Map<String, Long> hvorMangeInnsjoerPerKontinent = Innsjoer.innsjoer.stream().collect(Collectors.groupingBy(Innsjoe::kontinent, counting()));
        return hvorMangeInnsjoerPerKontinent;
    }

    //III
    public static String listeMedNavn(){
        String listeMedNavn = Innsjoer.innsjoer.stream().map(Innsjoe::navn).collect(Collectors.joining("|"));
        return listeMedNavn;
    }

    //IIV
    public static Double gjennomsnittligAral(){
        Double gjennomsnittligAral = Innsjoer.innsjoer.stream().collect(Collectors.averagingDouble(Innsjoe::areal));
        return gjennomsnittligAral;
    }

    //V
    public static Map<Boolean, List<Innsjoe>> toLister(){
        Map<Boolean, List<Innsjoe>> toLister = Innsjoer.innsjoer.stream().collect(Collectors.partitioningBy(i -> i.maksDybde() > 500));
        return toLister;
    }



    /**
     * Oppgave5
     * Flatmap
     * i Returner en liste med alle landene som er representert i lista.
     * ii Returner en liste med alle landene som er representert i lista, uten duplikater.
     * iii Returner en liste med alle landene som er representert i lista, og tell antall ganger hvert land er representert.
     */
    //I
    public static List<String> listeMedLand(){
        return Innsjoer.innsjoer.stream().flatMap(Innsjoe -> Innsjoe.land().stream()).collect(Collectors.toList());
        //return listeMedLand;
    }

    //II
    public static List<String> listeMedUnikeLand(){
        List<String> listeMedUnikeLand = Innsjoer.innsjoer.stream().flatMap(Innsjoe -> Innsjoe.land().stream()).distinct().collect(Collectors.toList());
        return listeMedUnikeLand;
    }

    //III
    public static Map<String, Long> alleLandmedAntall(){
        Map<String, Long> alleLandmedAntall = Innsjoer.innsjoer.stream().flatMap(Innsjoe -> Innsjoe.land().stream()).collect(Collectors.groupingBy(x -> x, counting()));
        return alleLandmedAntall;
    }
}
