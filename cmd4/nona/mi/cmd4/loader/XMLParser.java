package nona.mi.cmd4.loader;

import nona.mi.cmd4.main.Game;
import nona.mi.cmd4.scene.ChoiceScene;
import nona.mi.cmd4.scene.Scene;
import nona.mi.cmd4.scene.ScenePackage;
import nona.mi.cmd4.scene.StandardScene;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

public class XMLParser {



    public static void parse(Game game, String path, ScenePackage scenePackage) {

        try {

            //CARREGA O ARQUIVO
            File file = new File(path);
            if (!(file.exists())) {
                System.out.println("XML PACK NOT FOUND: " + file.getAbsolutePath());
                System.exit(0);
            }
            Document document = Jsoup.parse(file, "UTF-8");

            //TOTAL SCENES
            int totalScenes = Integer.parseInt((document.selectFirst("totalscenes")).text());

            //PACKAGE
            int pack = Integer.parseInt((document.selectFirst("package")).text());

            //CONFIGURACAO PARA O LOOP
            String scene = "scene";
            int id = 0;
            String sceneId = scene + id;


            for (int i = 0; i < totalScenes; i++) {

                //TYPE
                String tempType = document.selectFirst(sceneId + " type").text();

                //ID
                int tempId = Integer.parseInt(document.selectFirst(sceneId + " id").text());

                //NEXT SCENE
                int tempNextScene = Integer.parseInt(document.selectFirst(sceneId + " nextscene").text());
                int tempNextPack = Scene.NO_PACK;
                if (tempNextScene == Scene.LAST_SCENE) {
                    //NEXT PACK
                    tempNextPack = Integer.parseInt(document.selectFirst(sceneId + " nextpack").text());
                }

                //GRAPHIC
                String tempGraphic = null;
                try {
                    tempGraphic = document.selectFirst(sceneId + " graphic").text();
                } catch (Exception ex) {

                }

                if (tempType.equalsIgnoreCase("stan")) {

                    //TEXT T
                    Elements tempTexts = document.select(sceneId + " text t");
                    int totalTexts = tempTexts.size();
                    String[] dialogs = null;
                    if (totalTexts > 0) {
                        dialogs = new String[totalTexts];
                        int textId = 0;
                        for (Element tempText : tempTexts) {
                            dialogs[textId] = tempText.text();
                            textId++;
                        }
                    }

                    StandardScene tempStandardScene = null;

                    if (tempNextScene == Scene.LAST_SCENE) {
                        tempStandardScene = new StandardScene(game, game.getImageDB().get(tempGraphic), pack, tempId, tempNextPack, tempNextScene);
                    } else {
                        tempStandardScene = new StandardScene(game, game.getImageDB().get(tempGraphic), pack, tempId, tempNextScene);
                    }

                    if (dialogs != null) {
                        tempStandardScene.setDialog(dialogs);
                    }

                    scenePackage.put(tempStandardScene.getSceneId(), tempStandardScene);

                } else if (tempType.equalsIgnoreCase("choice")) {

                    //DESCRIPTION
                    String tempDescription = null;
                    try {
                        tempDescription = document.selectFirst(sceneId + " description").text();
                    } catch (Exception ex) {

                    }

                    //OPTIONS OP
                    Elements tempOptions = document.select(sceneId + " options op");
                    int totalOptions = tempOptions.size();
                    String[] options = null;
                    if (totalOptions > 0) {
                        options = new String[totalOptions];
                        int optionId = 0;
                        for (Element tempOption : tempOptions) {
                            options[optionId] = tempOption.text();
                            optionId++;
                        }
                    }

                    //GOTOSCENE SCN
                    Elements tempGoToScenes = document.select(sceneId + " gotoscene scn");
                    int totalGoToScene = tempGoToScenes.size();
                    int[] goToScene = null;
                    if (totalGoToScene > 0) {
                        goToScene = new int[totalGoToScene];
                        int goToSceneId = 0;
                        for (Element tempGotoScene : tempGoToScenes) {
                            goToScene[goToSceneId] = Integer.parseInt(tempGotoScene.text());
                            goToSceneId++;
                        }
                    }

                    //GOTOPACK PCK
                    Elements tempGoToPacks = document.select(sceneId + " gotopack pck");
                    int totalGoToPack = tempGoToPacks.size();
                    int[] goToPack = null;
                    if (totalGoToPack > 0) {
                        goToPack = new int[totalGoToPack];
                        int goToPackId = 0;
                        for (Element tempGoToPack : tempGoToPacks) {
                            goToPack[goToPackId] = Integer.parseInt(tempGoToPack.text());
                            goToPackId++;
                        }
                    }

                    ChoiceScene tempChoiceScene = new ChoiceScene(game, game.getImageDB().get(tempGraphic), pack, tempId, tempNextScene);
                    if (tempDescription != null) {
                        tempChoiceScene.setDescription(tempDescription);
                    }
                    tempChoiceScene.setChoices(options, goToScene, goToPack);
                    scenePackage.put(tempChoiceScene.getSceneId(), tempChoiceScene);

                }


                id++;
                sceneId = scene + id;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }

    }


}
