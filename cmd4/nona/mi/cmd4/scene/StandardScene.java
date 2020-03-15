package nona.mi.cmd4.scene;

import nona.mi.cmd4.dialog.Dialog;
import nona.mi.cmd4.main.Game;

public class StandardScene extends Scene {

    private Dialog dialog;



    public StandardScene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        super(game, image, packId, sceneId, nextScene);
    }

    public StandardScene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
        super(game, image, packId, sceneId, nextPack, nextScene);
    }

    public void setDialog(String[] allSceneDialog) {
        dialog = new Dialog(allSceneDialog);
    }

    @Override
    protected void updateClass(String playerInput) {
        if (dialog != null) {
            dialog.update();
            if (dialog.isEndDialog()) {
                if (nextScene == MAIN_SCENE || nextScene == CREDIT_SCENE) {
                    game.goToPublicSceneWithReset(nextScene);
                } else if (nextScene >= 0 || nextScene == LAST_SCENE) {
                    game.getSave().getTracer().add(sceneId);
                    game.goToNextSceneWithReset();
                } else {
                    //virah para cah se nextScene == NO_SCENE
                    dialog.reset();
                }
            }
        } else {
        	if (nextScene == MAIN_SCENE || nextScene == CREDIT_SCENE) {
                game.goToPublicSceneWithReset(nextScene);
            } else if (nextScene >= 0 || nextScene == LAST_SCENE) {
                game.getSave().getTracer().add(sceneId);
                game.goToNextSceneWithReset();
            }
        }
    }

    @Override
    public void renderClass() {
        if (dialog != null) {
            dialog.render();
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    @Override
    public void reset() {
        if (dialog != null) {
            dialog.reset();
        }
    }

}