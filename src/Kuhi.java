import java.util.ArrayList;
import java.util.List;

public class Kuhi<E extends Comparable<E>> {
    //kuhja andmestruktuuri implementatsioon. see on vajalik väärtuste korrektselt Huffmani kodeeringu puusse
    //paigutamiseks kuna Huffmani puu puhul kehtib sama tingimus mis kuhja andmestruktuuri puhul, et iga tipu väärtus
    //peab olema alamtippude väärtustest suurem või võrdne.

    //loob tühja järjendi kuhu hakatakse võrreldavaid objekte lisama
    private List<E> objektideMassiiv = new ArrayList<>();

    //konstruktor
    public Kuhi() {
    }

    //lisab kuhja uue objekti
    public void lisaKuhja(E objekt) {
        objektideMassiiv.add(objekt); //lisab objekti kuhja
        int antudIndeks = objektideMassiiv.size() - 1; //viimase tipu indeks

        while (antudIndeks > 0) {
            int ülemIndeks = (antudIndeks - 1) / 2;
            //kui antud objekt on suurema väärtusega kui selle ülemtipp, siis vahetab kohad ära
            if (objektideMassiiv.get(antudIndeks).compareTo(objektideMassiiv.get(ülemIndeks)) > 0) {
                E ajutine = objektideMassiiv.get(antudIndeks);
                objektideMassiiv.set(antudIndeks, objektideMassiiv.get(ülemIndeks));
                objektideMassiiv.set(ülemIndeks, ajutine);
            } else {
                break;
            }

            antudIndeks = ülemIndeks;
        }
    }

    //eemaldab kuhjast juurelemendi
    public E eemaldaKuhjast() {
        if (objektideMassiiv.size() == 0) {
            return null;
        }

        E eemaldatudObjekt = objektideMassiiv.get(0);
        objektideMassiiv.set(0, objektideMassiiv.get(objektideMassiiv.size() - 1));
        objektideMassiiv.remove(objektideMassiiv.size() - 1);

        int antudIndeks = 0;
        while (antudIndeks < objektideMassiiv.size()) {
            int vasakAlamIndeks = 2 * antudIndeks + 1;
            int paremAlamIndeks = 2 * antudIndeks + 2;

            //leiab kahe elemendi vahel maksimumi
            if (vasakAlamIndeks >= objektideMassiiv.size()) {
                break;
            }
            int maksimaalneIndeks = vasakAlamIndeks;
            if (paremAlamIndeks < objektideMassiiv.size()) {
                if (objektideMassiiv.get(maksimaalneIndeks).compareTo(objektideMassiiv.get(paremAlamIndeks)) < 0) {
                    maksimaalneIndeks = paremAlamIndeks;
                }
            }

            //vahetab kohad ära kui antud tipp on väiksem kui maksimum
            if (objektideMassiiv.get(antudIndeks).compareTo(objektideMassiiv.get(maksimaalneIndeks)) < 0) {
                E ajutine = objektideMassiiv.get(maksimaalneIndeks);
                objektideMassiiv.set(maksimaalneIndeks, objektideMassiiv.get(antudIndeks));
                objektideMassiiv.set(antudIndeks, ajutine);
                antudIndeks = maksimaalneIndeks;
            } else {
                break;
            }
        }

        return eemaldatudObjekt;
    }

    //tagastab tippude arvu puus
    public int massiiviSuurus() {
        return objektideMassiiv.size();
    }
}

