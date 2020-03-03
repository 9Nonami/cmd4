package nona.mi.cmd4.scene;

import nona.mi.cmd4.main.Game;

public class MainScene extends Scene {

    private String message;



    public MainScene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        super(game, image, packId, sceneId, nextScene);
    }

    public MainScene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
        super(game, image, packId, sceneId, nextPack, nextScene);
    }

    @Override
    protected void updateClass(String playerInput) {
        if (playerInput.equalsIgnoreCase("start")) {
            game.loadPack(0, 0);
        } else if (playerInput.equalsIgnoreCase("credits")) {
            game.goToPublicSceneWithReset(CREDIT_SCENE);
        }
    }

    @Override
    protected void renderClass() {
        System.out.println(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void reset() {

    }
}
