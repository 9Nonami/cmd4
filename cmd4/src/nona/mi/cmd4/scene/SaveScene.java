package nona.mi.cmd4.scene;

import nona.mi.cmd4.main.Game;


public class SaveScene extends Scene {

    private int tempPack;
    private int tempScene;



    public SaveScene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        super(game, image, packId, sceneId, nextScene);
    }

    public SaveScene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
        super(game, image, packId, sceneId, nextPack, nextScene);
    }

    @Override
    protected void updateClass(String playerInput) {

        if (playerInput.equalsIgnoreCase("return")) {
            nextPack = tempPack;
            nextScene = tempScene;
            game.goToNextSceneWithReset();
            return;
        }

        try {
            int chosenSlot = Integer.parseInt(playerInput);
            if (game.getSave().contains(chosenSlot)) {
                game.getSave().save(chosenSlot, tempPack, tempScene);
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
