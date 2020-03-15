package nona.mi.cmd4.scene;

import nona.mi.cmd4.main.Game;

public class CreditScene extends Scene {

    private String credits;



    public CreditScene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        super(game, image, packId, sceneId, nextScene);
    }

    public CreditScene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
        super(game, image, packId, sceneId, nextPack, nextScene);
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    @Override
    protected void updateClass(String playerInput) {
        game.goToPublicSceneWithReset(Scene.MAIN_SCENE);
    }

    @Override
    protected void renderClass() {
        if (credits != null) {
            for (int i = 0; i < credits.length(); i++) {
                if (credits.charAt(i) == '@') {
                    System.out.println("");
                    continue;
                }
                System.out.print(credits.charAt(i));
            }
            System.out.println("");
        }
    }

    @Override
    public void reset() {

    }

}
