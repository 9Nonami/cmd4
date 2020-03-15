package nona.mi.cmd4.scene;

import nona.mi.cmd4.main.Game;
import nona.mi.cmd4.save.Save;



public class ChoiceScene extends Scene {

    private String description;
    private String[] choices;
    private int[] possibleScenes;
    private int[] possiblePacks;


    public ChoiceScene(Game game, char[] image, int packId, int sceneId, int nextScene) {
        super(game, image, packId, sceneId, nextScene);
    }

    public ChoiceScene(Game game, char[] image, int packId, int sceneId, int nextPack, int nextScene) {
        super(game, image, packId, sceneId, nextPack, nextScene);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChoices(String[] choices, int[] possibleScenes, int[] possiblePacks) {
        this.choices = choices;
        this.possibleScenes = possibleScenes;
        this.possiblePacks = possiblePacks;
    }

    @Override
    protected void updateClass(String playerInput) {
        try {
            int tempChoice = Integer.parseInt(playerInput);
            if (tempChoice < choices.length && tempChoice >= 0) {
                nextScene = possibleScenes[tempChoice];
                if (nextScene == MAIN_SCENE || nextScene == CREDIT_SCENE) {
                    game.goToPublicSceneWithReset(nextScene);
                } else if (nextScene == LAST_SCENE && possiblePacks != null) {
                    nextPack = possiblePacks[tempChoice];
                    game.goToNextSceneWithReset();
                } else if (nextScene >= 0) {
                    game.goToNextSceneWithReset();
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    protected void renderClass() {
        renderDescription();
        renderChoices();
    }

    private void renderDescription() {
        if (description != null){
            for (int i = 0; i < description.length(); i++) {
                if (description.charAt(i) == '@') {
                    System.out.println("");
                    continue;
                }
                System.out.print(description.charAt(i));
            }
            System.out.println("");
        }
    }

    private void renderChoices() {
        for (int i = 0; i < choices.length; i++) {
            System.out.println("[" + i + "] " + choices[i]);
        }
    }

    public String getDescriptionForSave() {
    	if (description != null) {
    		if (description.contains("@")) {
    			description = description.replace('@', ' ');
    		}
    		if (description.length() > Save.SAVE_TEXT_OFFSET) {
    			return description.substring(0, Save.SAVE_TEXT_OFFSET);
    		} else {
    			return description;
    		}
    	}
    	return "Empty description";
    }

    @Override
    public void reset() {

    }

}
