package nona.mi.cmd4.save;

import nona.mi.cmd4.loader.TextLoader;
import nona.mi.cmd4.main.Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Save {

    private String[] slots; //contem "p-s"; o &\n eh adicionado no loop
    private File folderPath;
    private File savePath;



    public Save(Game game, int totalSlots) {

        slots = new String[totalSlots];
        folderPath = new File("");

        if (game.getOs().startsWith("l")) {
            savePath = new File(folderPath.getAbsolutePath() + "/save.9");
        } else if (game.getOs().startsWith("w")) {
            savePath = new File (folderPath.getAbsolutePath() + "\\save.9");
        } else {
            System.out.println("I guess you're not using Linux or Windows. The save file for this system was not implemented yet. Please see the Save.java to adjust it.");
            System.exit(0);
        }

        //CRIA O ARQUIVO
        if (!(savePath.exists())) {
            try {
                savePath.createNewFile();
                createBasicSaveFile(savePath);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        load();

    }

    private void createBasicSaveFile(File savePath) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))){
            for (int i = 0; i < slots.length; i++) {
                bw.write("0-0&\n");
            }
            bw.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void load() {
        String loaded = TextLoader.loadFromDisk(savePath);
        String[] splitted = loaded.split("&");
        System.arraycopy(splitted, 0, slots, 0, slots.length); //copia o array lido para o array da classe (slots)
    }

    public void save(int slot, int pack, int scene) {
        slots[slot] = "" + pack + "-" + scene;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            for (String s : slots) {
                bw.write(s + "&\n");
            }
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int slot) {
        slots[slot] = "0-0";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            for (String s : slots) {
                bw.write(s + "&\n");
            }
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getPackOfSlot(int slot) {
        return Integer.parseInt(slots[slot].split("-")[0]);
    }

    public int getSceneOfSlot(int slot) {
        return Integer.parseInt(slots[slot].split("-")[1]);
    }

    public String[] getSlots() {
        return slots;
    }

    public boolean contains(int id) {
        return ((id >= 0) && (id < slots.length));
    }

    public File getFolderPath() {
        return folderPath;
    }
}
