package nona.mi.cmd4.main;

import nona.mi.cmd4.db.ImageDB;
import nona.mi.cmd4.loader.DirectoryLoader;
import nona.mi.cmd4.loader.TextLoader;
import nona.mi.cmd4.loader.XMLParser;
import nona.mi.cmd4.save.Save;
import nona.mi.cmd4.scene.*;

import java.io.File;
import java.util.Scanner;



public class Game {

    private Scene sceneBasis;
    private ScenePackage packBasis;
    private ScenePackage publicScenesPackage;
    private Scanner scanner;
    private Save save;
    private String os;
    private boolean running;
    private String[] allPacks; //nomes dos arquivos pack (ex.: pack9.xml)
    private ImageDB imageDB; //temporario, muda de acordo com o pack
    private ImageDB publicImages; //contem main, save, load, copy e delete
    private int graphicWidth;
    private int graphicHeight;

    public static final int TOTAL_TRACE = 10;


    public Game() {
        os = "" + System.getProperty("os.name").toLowerCase().charAt(0);
        scanner = new Scanner(System.in);
        save = new Save(this, 5);
        initGraphicSize();
        initMainComponents();
        createPublicScenes();
        defineFirstScene();
    }

    private void initGraphicSize() {
        String sizes = TextLoader.loadFromDisk(new File(createPath(new String[]{"window.9"})));
        String[] spl = sizes.split("x");
        graphicWidth = Integer.parseInt(spl[0]);
        graphicHeight = Integer.parseInt(spl[1]);
    }

    private void createPublicScenes() {

        publicScenesPackage = new ScenePackage();

        MainScene m = new MainScene(this, publicImages.get("main"), Scene.NO_PACK, Scene.MAIN_SCENE, Scene.NO_SCENE);
        m.setMessage("Type start to begin a new game.\nType load to choose a progress.\nType credits to see the credits.");
        publicScenesPackage.put(m.getSceneId(), m);

        LoadScene loadScene = new LoadScene(this, publicImages.get("load"), Scene.NO_PACK, Scene.LOAD_SCENE, Scene.NO_SCENE);
        publicScenesPackage.put(loadScene.getSceneId(), loadScene);

        SaveScene saveScene = new SaveScene(this, publicImages.get("save"), Scene.NO_PACK, Scene.SAVE_SCENE, Scene.NO_SCENE);
        publicScenesPackage.put(saveScene.getSceneId(), saveScene);

        CopyScene copyScene = new CopyScene(this, publicImages.get("copy"), Scene.NO_PACK, Scene.COPY_SCENE, Scene.NO_SCENE);
        publicScenesPackage.put(copyScene.getSceneId(), copyScene);

        DeleteScene deleteScene = new DeleteScene(this, publicImages.get("delete"), Scene.NO_PACK, Scene.DELETE_SCENE, Scene.NO_SCENE);
        publicScenesPackage.put(deleteScene.getSceneId(), deleteScene);

        CreditScene creditScene = new CreditScene(this, null, Scene.NO_PACK, Scene.CREDIT_SCENE, Scene.NO_SCENE);
        creditScene.setCredits(TextLoader.loadFromDisk(new File(createPath(new String[]{"credits.txt"}))));
        publicScenesPackage.put(creditScene.getSceneId(), creditScene);

        HistoryScene historyScene = new HistoryScene(this, null, Scene.NO_PACK, Scene.HISTORY_SCENE, Scene.NO_SCENE);
        publicScenesPackage.put(historyScene.getSceneId(), historyScene);

    }

    private void defineFirstScene() {
        sceneBasis = publicScenesPackage.getScene(Scene.MAIN_SCENE);
    }

    public void goToNextSceneWithReset() {
        int tempNextScene = sceneBasis.getNextScene();
        if (tempNextScene == Scene.LAST_SCENE) {
            save.getTracer().setEmptyTraces();
            int tempNextPack = sceneBasis.getNextPack();
            loadPack(tempNextPack, 0);
        } else {
            sceneBasis.reset();
            sceneBasis = packBasis.getScene(tempNextScene);
        }
    }

    public void loadPack(int packId, int sceneId){
        if (sceneBasis != null) {
            sceneBasis.reset();
        }
        packBasis = new ScenePackage();
        loadPackClass(packBasis, packId);
        sceneBasis = packBasis.getScene(sceneId);
    }

    private void loadPackClass(ScenePackage scenePackage, int packId) {

        //LEITURA DAS IMAGES DO PACK
        imageDB = new ImageDB(createPath(new String[]{"images", "pack" + packId}));

        //VERIFICA SE O PACK EXISTE E O INICIA
        String packToBeLoaded = "pack" + packId + ".xml";
        for (String pack : allPacks) {
            if (packToBeLoaded.equalsIgnoreCase(pack)) {
                XMLParser.parse(this, createPath(new String[]{"packs", packToBeLoaded}), scenePackage);
                break;
            }
        }

    }

    public void goToPublicSceneWithReset(int id) {
        sceneBasis.reset();
        sceneBasis = publicScenesPackage.getScene(id);
    }

    public void start() {
        running = true;
        while (running) {
            render();
            update();
        }
        System.out.println("GAME FINISHED");
    }

    public void update() {
        sceneBasis.update();
    }

    public void render() {
        sceneBasis.render();
    }

    public Scene getPublicScene(int id) {
        return publicScenesPackage.getScene(id);
    }

    public Scene getSceneOfCurrentPack(int id) {
        return packBasis.getScene(id);
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public String getOs() {
        return os;
    }

    public Save getSave() {
        return save;
    }

    public void finish() {
        running = false;
    }

    public ImageDB getImageDB() {
        return imageDB;
    }

    private void initMainComponents() {
        allPacks = new DirectoryLoader().loadFileNames(createPath(new String[]{"packs"}));
        publicImages = new ImageDB(createPath(new String[]{"images"}));
    }

    private String createPath(String[] appends) {
        StringBuilder path = new StringBuilder(save.getFolderPath().getAbsolutePath());
        if (os.startsWith("w")) {
            for (String append : appends) {
                path.append("\\").append(append);
            }
        } else if (os.startsWith("l")) {
            for (String append : appends) {
                path.append("/").append(append);
            }
        }
        return path.toString();
    }

    public int getGraphicWidth() {
        return graphicWidth;
    }

    public int getGraphicHeight() {
        return graphicHeight;
    }

}
