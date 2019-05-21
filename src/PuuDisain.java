import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class PuuDisain extends Pane {
    //klass mis määrab ära puu disaini ja tekstiväljad

    //klassis vajaminevad isendiväljad
    private Kodeerimine.Puu puu;
    private String[] kodeering;
    private Text staatus;
    private double raadius = 15; //vaikimisi väärtus tippude suurusele
    private double vertikaalneVahe = 50; //vaikimisi vertikaalne vahe tippude vahel
    private static StringBuilder kodeerimine;

    //programmi käivitamisel on puu tühi ja vastav sõnum kuvatakse
    public PuuDisain() {
        puu = null;
        kodeering = null;
        //määrab ära määraStaatus paigutuse ekraanil ning kuna tulemust alguses ei ole, siis kuvatakse tühi sõne
        staatus = new Text(20, 20, "");
        määraStaatus("Puu on praegusel hetkel tühi");
    }

    //kui sisestatakse sobilik sisend, siis muudetakse määraStaatus abil kuvatav väljund ära
    public void määraStaatus(String sõnum) {
        getChildren().remove(staatus);
        staatus.setText(sõnum);
        getChildren().add(staatus);
    }

    //kodeerib sisendi Huffmani kodeeringus ning annab vastava tulemuse mis antakse määraStaatus meetodile ja väljastatakse
    public void näitaTekst(String tekst) {
        int[] sagedused = Kodeerimine.tähtedeSagedused(tekst);
        puu = Kodeerimine.huffmaniPuu(sagedused);
        kodeering = Kodeerimine.koodid(puu.juur);
        kuvaPuu();
        kodeerimine = new StringBuilder();
        for (int i = 0; i < tekst.length(); i++) {
            char c = tekst.charAt(i);
            kodeerimine.append(kodeering[(int) c]);
        }
        määraStaatus("Sisend " + "'" + tekst + "'" + " on kodeeritud " + kodeerimine.toString());
    }

    //puu kuvamise meetod ilma parameetriteta
    public void kuvaPuu() {
        this.getChildren().clear();
        if (puu.juur != null) {
            kuvaPuu(puu.juur, getWidth() / 2, vertikaalneVahe, getWidth() / 4);
        }
    }

    //puu loomise meetod kus argumentideks on puu juur, tipu x ja y koordinaadid ning tippude omavaheline horisontaalne vahe
    //if lausete abil luuakse rekursiivselt puu vasak ja parem pool ning määratakse kus koordinaatidel nende poolte tipud asuvad
    //vahel jooksevad osad nullid ja ühed üle tippe ühendavate joonte. seda ei saa koodist välja ilma numbreid joontest väga kaugele liigutamata
    public void kuvaPuu(Kodeerimine.Puu.Tipp juur, double xKoordinaat, double yKoordinaat,
                        double horisontaalneVahe) {
        if (juur.vasak != null) {
            Text tekstiKodeerimine = new Text(xKoordinaat + horisontaalneVahe / 2, yKoordinaat + vertikaalneVahe / 2, "0");
            getChildren().addAll(new Line(xKoordinaat - horisontaalneVahe, yKoordinaat + vertikaalneVahe, xKoordinaat, yKoordinaat), tekstiKodeerimine);
            kuvaPuu(juur.vasak, xKoordinaat - horisontaalneVahe, yKoordinaat + vertikaalneVahe, horisontaalneVahe / 2);
        }

        if (juur.parem != null) {
            Text tekstiKodeerimine = new Text(xKoordinaat - horisontaalneVahe / 2, yKoordinaat + vertikaalneVahe / 2, "1");
            getChildren().addAll(new Line(xKoordinaat + horisontaalneVahe, yKoordinaat + vertikaalneVahe, xKoordinaat, yKoordinaat), tekstiKodeerimine);
            kuvaPuu(juur.parem, xKoordinaat + horisontaalneVahe, yKoordinaat + vertikaalneVahe, horisontaalneVahe / 2);
        }

        //tippudeks olevate ringide disain
        Circle tipuDisain = new Circle(xKoordinaat, yKoordinaat, raadius);
        tipuDisain.setFill(Color.WHITE);
        tipuDisain.setStroke(Color.BLACK);
        Text täheMärk = new Text(xKoordinaat - 4, yKoordinaat + 30, juur.element + "");
        getChildren().addAll(tipuDisain, täheMärk, new Text(xKoordinaat - 4, yKoordinaat + 4, juur.kaal + ""));
    }

    //kodeerimine isendivälja get-meetod
    public static StringBuilder getKodeerimine() {
        return kodeerimine;
    }
}
