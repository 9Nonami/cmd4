package nona.mi.cmd4.save;

import nona.mi.cmd4.loader.TextLoader;
import nona.mi.cmd4.main.Game;
import nona.mi.cmd4.scene.Scene;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Save {

    private String[] slots; //contem "p-s"; o &\n eh adicionado no loop
    private String[] traces;
    private File folderPath;
    private File savePath;
    private Tracer tracer; //armazena os ids das cenas esolhidas



    public Save(Game game, int totalSlots) {

        tracer = new Tracer();

        traces = new String[totalSlots];
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
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            String basicTrace = "" + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE;
            for (int i = 0; i < slots.length; i++) {
                bw.write("0-0;" + basicTrace + "&\n");
            }
            bw.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void load() { //0-0;-97,-97,-97,-97,-97,-97,-97,-97,-97,-97&
        String loaded = TextLoader.loadFromDisk(savePath);
        String[] splitted = loaded.split("&");
        for (int i = 0; i < slots.length; i++) {
            String[] sp = splitted[i].split(";");
            slots[i] = sp[0];
            traces[i] = sp[1];
        }
    }

    public void save(int slot, int pack, int scene) {
        slots[slot] = "" + pack + "-" + scene;
        traces[slot] = tracer.getTracesAsString();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            for (int i = 0; i < slots.length; i++) {
                bw.write(slots[i] + ";" + traces[i] + "&\n");
            }
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int slot) {
        slots[slot] = "0-0";
        traces[slot] = "" + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            for (int i = 0; i < slots.length; i++) {
                bw.write(slots[i] + ";" + traces[i] + "&\n");
            }
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initTracer(int slot) {
        String[] ss = traces[slot].split(",");
        int[] temp = new int[ss.length];
        for (int i = 0; i < ss.length; i++) {
            temp[i] = Integer.parseInt(ss[i]);
        }
        tracer.setTraces(temp);
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

    public Tracer getTracer() {
        return tracer;
    }

}
