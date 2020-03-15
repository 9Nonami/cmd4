package nona.mi.cmd4.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TextLoader {

    public static String loadFromDisk(File file) {

        StringBuilder stringao = new StringBuilder();
        String read = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while (((read = br.readLine()) != null)) {
                stringao.append(read);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("TEXT READING ERROR");
            System.exit(0);
        }

        return stringao.toString();
    }



}
