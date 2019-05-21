import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;

public class PuuFunktsionaalsus extends BorderPane {
    //klass mis määrab ära kasutajaliidese funktsionaalsuse

    private static File fail = null;

    //kutsub välja meetodi mis loob kasutajaliidese
    public PuuFunktsionaalsus() {
        looKasutajaliides();
    }

    private void looKasutajaliides() {
        //loob klassi PuuDisain objekti
        PuuDisain puu = new PuuDisain();
        //loome tekstiväljad, nupud ning teksti nende peale ja lisame elemendid stseenile
        Text tekstiSisestamine = new Text("Sisesta tekst mida soovid kodeerida:");
        Text bittideSisestamine = new Text("Fail:");
        TextField tekstiväli1 = new TextField();
        TextField tekstiväli2 = new TextField();
        tekstiväli1.setPrefWidth(300);
        tekstiväli2.setPrefWidth(300);
        Button puuNupp = new Button("Huffmani Puu");
        Button failiValimisNupp = new Button("Vali Fail");
        Button loeFailNupp = new Button("Loe Failist");
        Button kirjutaFailNupp = new Button("Kirjuta Faili");

        HBox horisontaalneSõned = new HBox(10);
        horisontaalneSõned.getChildren().addAll(tekstiSisestamine, tekstiväli1, puuNupp);
        horisontaalneSõned.setAlignment(Pos.CENTER);
        HBox horisontaalneBitid = new HBox(10);
        horisontaalneBitid.getChildren().addAll(bittideSisestamine, tekstiväli2, failiValimisNupp, loeFailNupp, kirjutaFailNupp);
        horisontaalneBitid.setAlignment(Pos.CENTER);
        VBox vertikaalneTekst = new VBox(10);
        vertikaalneTekst.getChildren().addAll(horisontaalneSõned, horisontaalneBitid);
        vertikaalneTekst.setAlignment(Pos.CENTER);

        //kasutajaliidese nuppude reageerivaks muutmine
        puuNupp.setOnAction(e -> {
            //annab veateate kui proovitakse genereerida puud tühja tekstiväljaga
            Alert alert = new Alert(Alert.AlertType.ERROR, "Tekstiväli on tühi!", ButtonType.OK);
            alert.setTitle("Viga!");
            alert.setHeaderText(null);//eemaldame ebavajalikku kasti

            //annab heliga märku kui proovitakse sisestada tühi puu
            if (tekstiväli1.getText().isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                alert.show();
                //annab märku kui teksti pikkus vähem kui kaks tähemärki
            } else if (tekstiväli1.getText().length() < 2) {
                alert.setContentText("Peab olema vähemalt 2 tähemärki!");
                Toolkit.getDefaultToolkit().beep();
                alert.show();
                //annab märku kui teksti pikkus rohkem kui 50 tähemärki
            } else if (tekstiväli1.getText().length() > 50) {
                alert.setContentText("Ei saa olla rohkem kui 50 tähemärki!");
                Toolkit.getDefaultToolkit().beep();
                alert.show();
                //saadab teksti kodeerimiseks
            } else {
                puu.näitaTekst(tekstiväli1.getText());
            }
        });

        failiValimisNupp.setOnAction(e -> {
            //kui sisendiks on fail
            final FileChooser fileChooser = new FileChooser();
            fail = fileChooser.showOpenDialog(Peaklass.getLava());
            if (fail.getAbsolutePath().endsWith(".txt")) {
                tekstiväli2.setText(fail.getAbsolutePath());
            } else {
                //veateade kui fail pole .txt laiendiga
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fail ei ole .txt laiendiga", ButtonType.OK);
                alert.setTitle("Viga!");
                alert.setHeaderText(null);
                Toolkit.getDefaultToolkit().beep();
                alert.show();
            }
        });

        loeFailNupp.setOnAction(e -> {

            //loeme failist
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fail.getAbsolutePath())))) {
                String rida = bufferedReader.readLine();
                if (rida == null) {
                    //veateade kui fail on tühi
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fail on tühi!", ButtonType.OK);
                    alert.setTitle("Viga!");
                    alert.setHeaderText(null);
                    Toolkit.getDefaultToolkit().beep();
                    alert.show();
                } else {
                    //omistame tekstiväljale teksti
                    tekstiväli1.setText(rida);
                    //saadame teksti kodeerimiseks
                    puu.näitaTekst(rida);
                }
                //püütavad erindid
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sisestage fail!", ButtonType.OK);
                alert.setTitle("Viga!");
                alert.setHeaderText(null);
                Toolkit.getDefaultToolkit().beep();
                alert.show();
            }
        });

        kirjutaFailNupp.setOnAction(e -> {
            loeFailNupp.fire();
            //kirjutame faili
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fail.getAbsolutePath())))) {
                bufferedWriter.write(tekstiväli1.getText() + " " + PuuDisain.getKodeerimine());
                //püütavad erindid
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sisestage fail!", ButtonType.OK);
                alert.setTitle("Viga!");
                alert.setHeaderText(null);
                Toolkit.getDefaultToolkit().beep();
                alert.show();
            }
        });
        //määrame teksti peale
        setTop(vertikaalneTekst);
        //määrame puu keskele
        setCenter(puu);
    }

}

