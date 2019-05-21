/**********
 * Objektorienteeritud programmeerimine (LTAT.03.003)
 * 2018/2019 kevadsemester
 * 2. Rühmatöö.
 *
 * Autorid: Kaarel Kaasla, Eduard Rudi
 *
 * Teema: Huffmani kodeeringu kahendpuuna visualiseerimine
 * Lühiülevaade programmi tööst: Huffmani kodeerimine on kontseptsioon arvutiteaduse valdkonnas mis võimaldab andmeid
 * "kokku pakkida" ilma igasuguse informatsioonikaota, aga samas faili mahtu vähendades. See toimib võttes aluseks
 * faili sisu kahendkoodis, analüüsides selles esinevate erinevate karakterite sagedust ning omistades ümber vähem bitte
 * võtvad kahendkodeeringud märkidele mida failis sagedamini eksisteerib. Selle tulemusena saadakse tulemuseks bitijada
 * mis annab edasi täpselt sama informatsiooni, aga on lühem esialgsest failist. Enim levinud viis Huffmani kodeeringu
 * visualiseerimiseks on kasutada kahendpuid kus puu esimesteks kaheks leheks saavad märgid mida esineb kõige vähem ning
 * seejärel hakatakse väärtuste kasvavas järjekorras kahendpuule väärtusi juurde panema kuni jõutakse juureni mis
 * tähistab failis enim esinevat märki. Antud programmi tööks ongi seda protsessi kahendpuuna visualiseerida.
 * Programm võimaldab sisestada sõna ning analüüsib seda tagastades Huffmani kodeeringus kahendpuu visualisatsiooni.
 * Lisaks võimaldab programm .txt laiendiga failist andmete sisu lugeda ja analüüsida ning saadud tulemuse uuesti
 * tekstifaili kirjutada. Antud programm on kirjutatud Java keeles ning graafilise liidese realiseerimiseks on
 * kasutatud JavaFX raamistikku.
 * Huffmani kodeeringu kohta saab rohkem lugeda:
 *  https://en.wikipedia.org/wiki/Huffman_coding
 *  https://medium.com/@TravCav/huffman-coding-1e914c419912
 *
 * Kui programmi käivitamisega on probleeme:
 *  -Project Structure -> Global Libraries peab olema JavaFX lisatud (programmi kirjutamiseks on kasutatud versioon 11)
 *  -Run -> Edit Configurations -> VM Options peab olema
 *  "--module-path [path] --add-modules=javafx.controls,javafx.fxml,javafx.base" (ilma jutumärkideta)
 *  kus [path] asemel on JavaFX SDK lib kausta asukoht kõvakettal, nt. C:\javafx-sdk-11.0.2\lib
 *  -Programmi kirjutamiseks on kasutatud Java 12 SDK
 **********/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Peaklass extends Application {
    //isendiväli selleks, et peaLava saaks kasutada teistes klassides
    private static Stage Lava;

    @Override
    public void start(Stage peaLava) {

        //loome klassi PuuFunktsionaalsus isendi
        PuuFunktsionaalsus puuIsend = new PuuFunktsionaalsus();

        //loome stseeni, määrame selle vaikimisi suuruse ning kuvame selle
        Scene stseen = new Scene(puuIsend, 800, 600);
        peaLava.setTitle("Huffmani Kodeeringu Visualiseerimine");
        peaLava.setScene(stseen);
        peaLava.show();
    }

    //lava getter meetod
    public static Stage getLava() {
        return Lava;
    }
}
