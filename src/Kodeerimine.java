import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

//Huffmani kodeeringu teooria implementatsioon.
public class Kodeerimine {
    //genereerib märkidele Huffmani kahendkoodide kodeeringu
    //väärtused välja toodud siin 'binaarkood' tulbas:
    //https://et.wikipedia.org/wiki/Huffmani_kodeerimine#Kodeerimistabeli_n%C3%A4ide
    //
    public static String[] koodid(Puu.Tipp juur) {
        if (juur == null) {
            return null;
        }
        String[] kahendKoodid = new String[2 * 128];
        määraKoodid(juur, kahendKoodid);
        return kahendKoodid;
    }

    //määrab rekursiivselt leht-tippudele kodeeringu
    private static void määraKoodid(Puu.Tipp juur, String[] koodid) {
        if (juur.vasak != null) {
            juur.vasak.kodeering = juur.kodeering + "0";
            määraKoodid(juur.vasak, koodid);

            juur.parem.kodeering = juur.kodeering + "1";
            määraKoodid(juur.parem, koodid);
        } else {
            koodid[(int) juur.element] = juur.kodeering;
        }
    }

    //loob vastavalt loodud koodidele Huffmani puu
    public static Puu huffmaniPuu(int[] esinemisteArv) {
        //loob kuhja andmestruktuuri kus hoitakse puid ja hakkab sinna lisama tippe
        Kuhi<Puu> kuhi = new Kuhi<>();
        for (int i = 0; i < esinemisteArv.length; i++) {
            if (esinemisteArv[i] > 0) {
                //loob kuhja mis koosneb ainult leht-puudest (ühe tipuga puud)
                kuhi.lisaKuhja(new Puu(esinemisteArv[i], (char) i));
            }
        }

        while (kuhi.massiiviSuurus() > 1) {
            //eemaldab kuhjast väikseima elemendi
            Puu puu1 = kuhi.eemaldaKuhjast();
            //eemaldab kuhjast teise väikseima elemendi
            Puu puu2 = kuhi.eemaldaKuhjast();
            //lisab tipud kuhja tagasi, aga loob neist enne puu ja lisab
            //individuaalisete tippude asemel neist tippudest koosneva puu
            kuhi.lisaKuhja(new Puu(puu1, puu2));
        }

        //saame valmis puu
        return kuhi.eemaldaKuhjast();
    }

    //arvutab märkide sagedused sõnas
    public static int[] tähtedeSagedused(String tekst) {
        int[] esinemistArv = new int[256]; //saab sisaldada 256 erinevat märki

        for (int i = 0; i < tekst.length(); i++) {
            esinemistArv[(int) tekst.charAt(i)]++; //loendab tekstis märgid kokku
        }
        return esinemistArv;
    }

    //defineerib Huffmani kodeerimispuu
    public static class Puu implements Comparable<Puu> {
        Tipp juur;

        //loob puu kahe erineva alampuuga
        public Puu(Puu puu1, Puu puu2) {
            juur = new Tipp();
            juur.vasak = puu1.juur;
            juur.parem = puu2.juur;
            juur.kaal = puu1.juur.kaal + puu2.juur.kaal;
        }

        //loob uue puu mis sisaldab lehte
        public Puu(int kaal, char element) {
            juur = new Tipp(kaal, element);
        }

        //võrdleb kahte puud nende kaalude põhjal, kui suurema kaaluga puu on all, siis
        //vahetab positsioonid ära
        @Override
        public int compareTo(Puu puu) {
            if (juur.kaal < puu.juur.kaal) {
                return 1;
            } else if (juur.kaal == puu.juur.kaal) {
                return 0;
            } else {
                return -1;
            }
        }

        public class Tipp {
            char element; //hoiustab lehes olevat märki
            int kaal; //alampuu kaal mingis kindlas tipus
            Tipp vasak; //vasakpoolne alampuu
            Tipp parem; //parempoolne alampuu
            String kodeering = ""; //mingi tipu ja puu juure vaheline kodeering

            //tühja tipu loomise konstruktor
            public Tipp() {
            }

            //kindla kaalu ja kindla märgiga elemendi loomise konstruktor
            public Tipp(int kaal, char element) {
                this.kaal = kaal;
                this.element = element;
            }
        }
    }
}

