package nona.mi.cmd4.scene;

import nona.mi.cmd4.dialog.Dialog;
import nona.mi.cmd4.main.Game;

public class HistoryScene extends Scene {

	private int id;
	private int tempPack;
    private int tempScene;
    private Dialog tempDialog;
    private boolean lock;



	public HistoryScene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        super(game, image, packId, sceneId, nextScene);
    	init();
    }

    public HistoryScene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
        super(game, image, packId, sceneId, nextPack, nextScene);
    	init();
    }

    private void init() {
    	id = 0;
        lock = false;
    }

    public void checkInitialId() {
        int[] tempTraces = game.getSave().getTracer().getTraces();
        for (int i = 0; i < tempTraces.length; i++) {
            if (tempTraces[i] != NO_SCENE) {
                id = i;
                return;
            }
        }
        id = tempTraces.length;
    }

    @Override
    protected void updateClass(String playerInput) {
    	if (playerInput.equalsIgnoreCase("return")) {
            nextPack = tempPack;
            nextScene = tempScene;
            game.goToNextSceneWithReset();
            return;
        }
        if (id < Game.TOTAL_TRACE) {
            if (tempDialog != null) {
                tempDialog.update();
                if (tempDialog.isEndDialog()) {
                    id++;
                    lock = false;
                    if (id == Game.TOTAL_TRACE) {
                        nextPack = tempPack;
                        nextScene = tempScene;
                        game.goToNextSceneWithReset();
                    }
                }
            }
        } else {
            nextPack = tempPack;
            nextScene = tempScene;
            game.goToNextSceneWithReset();
        }
    }

    @Override
    public void renderClass() {
        if (!lock && id < Game.TOTAL_TRACE) {
            lock = true;
            int tempSceneId = game.getSave().getTracer().getTraces()[id];
            StandardScene tempStan = (StandardScene) game.getSceneOfCurrentPack(tempSceneId);
            if (tempStan != null) {
                tempDialog = tempStan.getDialog();    
            }
        }
    	if (tempDialog != null && id < Game.TOTAL_TRACE) {
    		tempDialog.render();	
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
        for (int i = 0; i < Game.TOTAL_TRACE; i++) {
            int scn = game.getSave().getTracer().getTraces()[i];
            StandardScene std = (StandardScene) game.getSceneOfCurrentPack(scn);
            if (std != null) {
            	if (std.getDialog() != null) {
                	std.getDialog().reset();    
            	}
            }
        }
        id = 0;
        lock = false;
        tempDialog = null;
    }

}