package nona.mi.cmd4.scene;

import nona.mi.cmd4.main.Game;


public class LoadScene extends Scene {

    private int tempPack;
    private int tempScene;



    public LoadScene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        super(game, image, packId, sceneId, nextScene);
    }

    public LoadScene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
        super(game, image, packId, sceneId, nextPack, nextScene);
    }

    @Override
    protected void updateClass(String playerInput) {

        if (playerInput.equalsIgnoreCase("return")) {
            if (tempPack == NO_PACK) {
                game.goToPublicSceneWithReset(tempScene);
            } else {
                nextPack = tempPack;
                nextScene = tempScene;
                game.goToNextSceneWithReset();
            }
            return;
        }

        try {
            int chosenSlot = Integer.parseInt(playerInput);
            if (game.getSave().contains(chosenSlot)) {
                if (tempPack == game.getSave().getPackOfSlot(chosenSlot)) {
                    //esta dando load em cena do pack atual
                    nextPack = game.getSave().getPackOfSlot(chosenSlot);
                    nextScene = game.getSave().getSceneOfSlot(chosenSlot);
                    game.goToNextSceneWithReset();
                } else {
                    //load em pack diferente do atual
                    game.loadPack(game.getSave().getPackOfSlot(chosenSlot), game.getSave().getSceneOfSlot(chosenSlot));
                }
                //fill tracer
                game.getSave().initTracer(chosenSlot);
            }
        } catch (Exception ex) {

        }

    }

    @Override
    protected void renderClass() {
        String[] tempSlots = game.getSave().getSlots();
        for (int i = 0; i < tempSlots.length; i++) {
            System.out.println("[" + i + "] " + tempSlots[i]);
        }
    }

    public void setTempPack(int tempPack) {
        this.tempPack = tempPack;
    }

    public void setTempScene(int tempScene) {
        this.tempScene = tempScene;
    }

    @Override
    public void reset() {

    }

}
