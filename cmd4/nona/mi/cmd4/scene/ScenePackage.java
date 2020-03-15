package nona.mi.cmd4.scene;

import java.util.HashMap;

/**
 * Cria um "pacote" (hashMap) o qual armazenara as cenas.
 */
public class ScenePackage {

    private HashMap<Integer, Scene> scenePack;

    /**
     * Construtor.
     */
    public ScenePackage() {
        scenePack = new HashMap<Integer, Scene>();
    }

    /**
     * Armazena as cenas neste pacote.
     *
     * @param id a identificacao da cena
     * @param scene a cena em si
     */
    public void put(int id, Scene scene) {
        scenePack.put(id, scene);
    }

    /**
     * Retorna a cena especificada pelo id.
     *
     * @param id o id da cena desejada deste pacote.
     * @return a cena, caso exista. Do contrario, <code>null</code>
     * eh retornado.
     */
    public Scene getScene(int id) {
        if (scenePack.containsKey(id)) {
            return scenePack.get(id);
        }
        return null;
    }


}
