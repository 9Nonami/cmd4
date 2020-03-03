package nona.mi.cmd4.db;

import nona.mi.cmd4.loader.DirectoryLoader;
import nona.mi.cmd4.loader.TextLoader;

import java.io.File;
import java.util.HashMap;

public class ImageDB {

    private HashMap<String, char[]> hashMap;

    public ImageDB(String path) {
        hashMap = new HashMap<String, char[]>();
        File[] allImages = new DirectoryLoader().load(path);
        if (allImages.length != 0) {
            for (int i = 0; i < allImages.length; i++) {
                String tempFileName = allImages[i].getName();
                String name = tempFileName.substring(0, tempFileName.lastIndexOf("."));
                hashMap.put(name, TextLoader.loadFromDisk(allImages[i]).toCharArray());
            }
        }
    }

    public char[] get(String key) {
        if (hashMap.containsKey(key)) {
            return hashMap.get(key);
        }
        return null;
    }

}
