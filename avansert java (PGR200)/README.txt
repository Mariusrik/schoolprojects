Forbehold og svar på spørsmål i Mappe innleveringen.

Maven:
mvn clean install
Skulle lagt inn mer dokumentasjon i maven, men ettersom det ble mer tungvint enn jeg hadde håpet har jeg ikke tid til å bruke tid på det.

Innlevering 1:
Database: MySQL
Databasen som blir brukt:
CREATE DATABASE rikmar
localhost 
Brukernavn: root
Passord: toor

Hva som kunne vært gjort bedre:
 * En del av testene mine er litt rotete.
 * Er ikke helt fornøyd med hvordan testene er gjort, og om jeg hadde hatt tid ville det blitt forbedret.

Innlevering 2:
Embedded database SQLite
scriptet for å opprette databasen legges ved

Hva som kunne vært gjort bedre:
* Serveren er rotete og ville vært neste ting jeg ville refakturert om jeg hadde hatt tid.
* Testene er heller ikke de beste og kommer av litt dårlig planlegging fra starten av.



Java 8:
 * Har laget dynamiske tester for noen, men ikke alle, da oppgaven hovedsakelig går ut på å Java 8 stream/lambda
 * Og ettersom eksempelet i oppgave 1.1 var lite dynamisk regnet jeg med at dette var greit. Men jeg bestemte meg 
	likvel etterhvert å lage tester som fungerer selv om dataen i innsjøene skulle blitt endret

ORM:
 * Dette er ment som en enkel demonstrasjon av ORM
 * Exeption handling er heller ikke gjort mye ut av ettersom dette ikke er produksjonskode.
 * Går ut i fra at det er nok for oppgaven å bare vise at man får til CRUD med ORMlite og at hoved-vektingen går på innleveringene.

Forklar hvordan du kan gå fram for å endre en av innleveringene dine til å bruke 
ORM, og fordeler/ulemper med å gjøre det.:
 * Eksempel på bruk av ORM i innlevering 2 kunne vært å ha et objekt for hver spørsmålskategori i stedet spørsmålstype containeren jeg har.
 * Det ville vært ganske enkelt å bytte over til ORM i innleveringen min egentlig fordi jeg har en Question interface og løse koblinger. 
	Slik at jeg kunne byttet ut SQL question med en ORM question. Og gjort noen endringer med QuestionTypeContaineren min.

 * Fordeler: En av fordelene med orm er at utviklingen skal kunne gå fortere, men for meg som er komfortabel med SQL og ikke hadde brukt ORM før
	var det likevel raskere å bruke JDBC
 * En annen fordel er at ORM ville gjort det enklere å sikre mot SQL injections.
 * Ville også sluppet å bruke en SQLGenerator for å opprette SQL spørringer.
 * Ulemper: 