package nona.mi.cmd4.scene;

import nona.mi.cmd4.main.Game;

public class CopyScene extends Scene {

    private int tempPack;
    private int tempScene;



    public CopyScene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        super(game, image, packId, sceneId, nextScene);
    }

    public CopyScene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
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
            String[] data = playerInput.split(" ");
            if (data.length == 2) {
                int origin = Integer.parseInt(data[0]);
                int destination = Integer.parseInt(data[1]);
                if (game.getSave().contains(origin) && game.getSave().contains(destination)) {
                    int tempOriginPack = game.getSave().getPackOfSlot(origin);
                    int tempOriginScene = game.getSave().getSceneOfSlot(origin);
                    game.getSave().save(destination, tempOriginPack, tempOriginScene);
                }
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
