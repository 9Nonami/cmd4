package nona.mi.cmd4.scene;

import nona.mi.cmd4.main.Game;


public abstract class Scene {

    private int sceneId;
    private int packId;
    private char[] image;
    protected int nextScene;
    protected int nextPack;
    protected Game game;
    public static final int LAST_SCENE = -99; //basicamente define a ultima cena do pack, indicando que um novo precisa ser lido. SE UMA CENA USAR ESSE ID, PACK NAO PODE DEIXAR DE SER ATRIBUIDO!
    public static final int NO_SCENE = -97; //usado em ChoiceScene para preencher o metodo com uma cena temporaria
    public static final int NO_PACK = -96; //usado em ChoiceScene para preencher o metodo com um pack temporario (caso alguma cena tenha o id de LAST_SCENE)
    public static final int MAIN_SCENE = -95;
    public static final int LOAD_SCENE = -94;
    public static final int SAVE_SCENE = -93;
    public static final int COPY_SCENE = -92;
    public static final int DELETE_SCENE = -91;
    public static final int CREDIT_SCENE = -90;



    public Scene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        this.game = game;
        this.image = image;
        this.packId = packId;
        this.sceneId = sceneId;
        this.nextScene = nextScene;
    }

    public Scene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
        this.game = game;
        this.image = image;
        this.packId = packId;
        this.sceneId = sceneId;
        this.nextPack = nextPack;
        this.nextScene = nextScene;
    }

    public void update() {
        String playerInput = game.getInput();
        if (checkPriorityCommands(playerInput)) {
            clearScreen();
            return;
        }
        updateClass(playerInput);
        clearScreen();
    }

    private boolean checkPriorityCommands(String playerInput) {
        if (playerInput.equalsIgnoreCase("quit")) {
            game.finish();
            return true;
        } else if (playerInput.equalsIgnoreCase("main") && sceneId != CREDIT_SCENE){
            game.goToPublicSceneWithReset(MAIN_SCENE);
            return true;
        } else if (playerInput.equalsIgnoreCase("load") && sceneId != LOAD_SCENE && sceneId != CREDIT_SCENE) {
            LoadScene tempLoadScene = (LoadScene) game.getPublicScene(LOAD_SCENE);
            tempLoadScene.setTempPack(packId);
            tempLoadScene.setTempScene(sceneId);
            game.goToPublicSceneWithReset(LOAD_SCENE);
            return true;
        } else if (playerInput.equalsIgnoreCase("save") && sceneId != MAIN_SCENE && sceneId != LOAD_SCENE && sceneId != SAVE_SCENE && sceneId != COPY_SCENE && sceneId != DELETE_SCENE && sceneId != CREDIT_SCENE) {
            SaveScene tempSaveScene = (SaveScene) game.getPublicScene(SAVE_SCENE);
            tempSaveScene.setTempPack(packId);
            tempSaveScene.setTempScene(sceneId);
            game.goToPublicSceneWithReset(SAVE_SCENE);
            return true;
        } else if (playerInput.equalsIgnoreCase("copy") && sceneId != COPY_SCENE && sceneId != CREDIT_SCENE) {
            CopyScene tempCopyScene = (CopyScene) game.getPublicScene(COPY_SCENE);
            tempCopyScene.setTempPack(packId);
            tempCopyScene.setTempScene(sceneId);
            game.goToPublicSceneWithReset(COPY_SCENE);
            return true;
        } else if (playerInput.equalsIgnoreCase("delete") && sceneId != DELETE_SCENE && sceneId != CREDIT_SCENE) {
            DeleteScene tempDeleteScene = (DeleteScene) game.getPublicScene(DELETE_SCENE);
            tempDeleteScene.setTempPack(packId);
            tempDeleteScene.setTempScene(sceneId);
            game.goToPublicSceneWithReset(DELETE_SCENE);
            return true;
        }

        return false;
    }

    private void clearScreen() {
        try {

            ProcessBuilder pb = null;

            if (game.getOs().startsWith("l")) {
                pb = new ProcessBuilder("clear");
            } else if (game.getOs().startsWith("w")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                System.out.println("os error");
                System.exit(0);
            }

            pb.inheritIO();
            Process p = pb.start();
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void updateClass(String playerInput);

    public void render() {
        drawImage();
        renderClass();
    }

    protected abstract void renderClass();

    private void drawImage() {
        if(image != null) {
            for (int i = 0; i < game.getGraphicWidth() * game.getGraphicHeight(); i++) {
                if (i != 0 && i % game.getGraphicWidth() == 0) {
                    System.out.println("");
                }
                System.out.print(image[i]);
                System.out.print(image[i]);
            }
            System.out.println("");
        }
    }

    public int getSceneId() {
        return sceneId;
    }

    public int getPackId() {
        return packId;
    }

    public int getNextScene() {
        return nextScene;
    }

    public int getNextPack() {
        return nextPack;
    }

    public abstract void  reset(); //esta cena em si nao tem nada para resetar

}
