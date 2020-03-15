package nona.mi.cmd4.save;

import nona.mi.cmd4.loader.TextLoader;
import nona.mi.cmd4.main.Game;
import nona.mi.cmd4.scene.Scene;
import nona.mi.cmd4.scene.StandardScene;
import nona.mi.cmd4.scene.ChoiceScene;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class Save {

	private Game game;
    private String[] slots; //contem "p-s"; o &\n eh adicionado no loop
    private String[] traces;
    private String[] sentences;
    private String[] dates;
    private File folderPath;
    private File savePath;
    private Tracer tracer; //armazena os ids das cenas esolhidas
    public static final int SAVE_TEXT_OFFSET = 36;



	//0-0;-97,-97,-97,-97,-97,-97,-97,-97,-97,-97;Empty;00/00/0000&
    public Save(Game game, int totalSlots) {

    	this.game = game;

        tracer = new Tracer();

        traces = new String[totalSlots];
        slots = new String[totalSlots];
        sentences = new String[totalSlots];
        dates = new String[totalSlots];

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
            String basicSave = "0-0;";
            String basicTrace = "" + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + ";";
            String basicSentence = "Empty;";
            String basicDate = "00/00/0000";
            for (int i = 0; i < slots.length; i++) {
                bw.write(basicSave + basicTrace + basicSentence + basicDate + "&\n");
            }
            bw.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void load() {
        String loaded = TextLoader.loadFromDisk(savePath);
        String[] splitted = loaded.split("&");
        for (int i = 0; i < slots.length; i++) {
            String[] sp = splitted[i].split(";");
            slots[i] = sp[0];
            traces[i] = sp[1];
            sentences[i] = sp[2];
            dates[i] = sp[3];
        }
    }

    public void save(int slot, int pack, int scene) {
        slots[slot] = "" + pack + "-" + scene;
        
        traces[slot] = tracer.getTracesAsString();

        Scene tmpScn = game.getSceneOfCurrentPack(scene);
        if (tmpScn instanceof StandardScene) {
        	StandardScene tempStan = (StandardScene) tmpScn;
        	sentences[slot] = tempStan.getDialog().getDialogForSave();
        } else if (tmpScn instanceof ChoiceScene) {
        	ChoiceScene tempChoice = (ChoiceScene) tmpScn;
        	sentences[slot] = tempChoice.getDescriptionForSave();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        dates[slot] = sdf.format(calendar.getTime());

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            for (int i = 0; i < slots.length; i++) {
                bw.write(slots[i] + ";" + traces[i] + ";" + sentences[i] + ";" + dates[i] + "&\n");
            }
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int slot) {
        slots[slot] = "0-0";
        traces[slot] = "" + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE + "," + Scene.NO_SCENE;
        sentences[slot] = "Empty";
        dates[slot] = "00/00/0000";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            for (int i = 0; i < slots.length; i++) {
                bw.write(slots[i] + ";" + traces[i] + ";" + sentences[i] + ";" + dates[i] + "&\n");
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

    public String[] getSentences() {
    	return sentences;
    }

    public String[] getDates() {
    	return dates;
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
