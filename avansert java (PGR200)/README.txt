Forbehold og svar p� sp�rsm�l i Mappe innleveringen.

Maven:
mvn clean install
Skulle lagt inn mer dokumentasjon i maven, men ettersom det ble mer tungvint enn jeg hadde h�pet har jeg ikke tid til � bruke tid p� det.

Innlevering 1:
Database: MySQL
Databasen som blir brukt:
CREATE DATABASE rikmar
localhost 
Brukernavn: root
Passord: toor

Hva som kunne v�rt gjort bedre:
 * En del av testene mine er litt rotete.
 * Er ikke helt forn�yd med hvordan testene er gjort, og om jeg hadde hatt tid ville det blitt forbedret.

Innlevering 2:
Embedded database SQLite
scriptet for � opprette databasen legges ved

Hva som kunne v�rt gjort bedre:
* Serveren er rotete og ville v�rt neste ting jeg ville refakturert om jeg hadde hatt tid.
* Testene er heller ikke de beste og kommer av litt d�rlig planlegging fra starten av.



Java 8:
 * Har laget dynamiske tester for noen, men ikke alle, da oppgaven hovedsakelig g�r ut p� � Java 8 stream/lambda
 * Og ettersom eksempelet i oppgave 1.1 var lite dynamisk regnet jeg med at dette var greit. Men jeg bestemte meg 
	likvel etterhvert � lage tester som fungerer selv om dataen i innsj�ene skulle blitt endret

ORM:
 * Dette er ment som en enkel demonstrasjon av ORM
 * Exeption handling er heller ikke gjort mye ut av ettersom dette ikke er produksjonskode.
 * G�r ut i fra at det er nok for oppgaven � bare vise at man f�r til CRUD med ORMlite og at hoved-vektingen g�r p� innleveringene.

Forklar hvordan du kan g� fram for � endre en av innleveringene dine til � bruke 
ORM, og fordeler/ulemper med � gj�re det.:
 * Eksempel p� bruk av ORM i innlevering 2 kunne v�rt � ha et objekt for hver sp�rsm�lskategori i stedet sp�rsm�lstype containeren jeg har.
 * Det ville v�rt ganske enkelt � bytte over til ORM i innleveringen min egentlig fordi jeg har en Question interface og l�se koblinger. 
	Slik at jeg kunne byttet ut SQL question med en ORM question. Og gjort noen endringer med QuestionTypeContaineren min.

 * Fordeler: En av fordelene med orm er at utviklingen skal kunne g� fortere, men for meg som er komfortabel med SQL og ikke hadde brukt ORM f�r
	var det likevel raskere � bruke JDBC
 * En annen fordel er at ORM ville gjort det enklere � sikre mot SQL injections.
 * Ville ogs� sluppet � bruke en SQLGenerator for � opprette SQL sp�rringer.
 * Ulemper: 