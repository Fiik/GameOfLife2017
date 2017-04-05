package sample;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.CharacterStringConverter;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;

import java.io.*;

public class FileHandler {

    private byte[][] newBoard = null;

    public void openFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open new game");


        //Fil-filter:
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("RLE", "*.rle"),
                new FileChooser.ExtensionFilter("Text-files", "*.txt")
        );
        File file = fileChooser.showOpenDialog(new Stage());

        try {
            readGameDisk(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readGameDisk(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file)); // lager objekt som leser.
        String line;                                                        // oppretter objektet line;
        int width = 0;
        int height = 0;

        while ((line = reader.readLine()) != null) { //leser linje for linje

            if (line.charAt(0) == 'x') { // Når den leser X.

                String[] splitx = line.split(","); //splitter etter komma

                for (String s : splitx) { // Går gjennom hver splitverdi.
                    int number = 0; //Begynner på index 0.
                    int plass = 1; // Siste tallet ganges med 1. Se kode lengre nede hvor plass ganges med 10. Dette for å få heltall til slutt.
                    for (int i = s.length() - 1; i >= 0; i--) {
                        if (Character.isDigit(s.charAt(i))) {
                            number += (s.charAt(i) - 48) * plass; // -48 pga ASCII (char).
                            plass *= 10; //looper bakover, derfor ganges med 10 (10-tallsystemet), for å få heltall.
                        }
                    }

                    if (width == 0) {
                        width = number;
                    } else if (height == 0) {
                        height = number;
                    } else {
                        newBoard = new byte[height][width];

                    }
                }
/* HENTE UT KOORDINATORER */

            } else if (newBoard != null) {

                String[] splity = line.split("\\$");

                int roundCount = 0;

                for (String y : splity) {
                    System.out.println(y);

                    //Denne linjen setter telleren.
                    //int roundCount = 0;

                   /* for (int i = y.length(); i >= 0; i++) {

                        if (y.charAt(i) == 'o') {                           // Hvis den treffer på "o" eller "b" eller tall.
                            System.out.println("1");
                        } else if (y.charAt(i) == 'b') {
                            System.out.println("0");
                        } else {
                            System.out.println("multipliseres med");
                        }


                    }
*/
                    //roundCount++;


                }


            }
        }
    }


        // Metode for å returnere det nye brettet som er lastet inn.
        public byte[][] getNewBoard () {
            return newBoard;
        }


    }

// Test av branch.

//runcount
// tall som kommer etter
// x * tegn som kommer etter













