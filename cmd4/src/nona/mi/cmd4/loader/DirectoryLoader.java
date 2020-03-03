package nona.mi.cmd4.loader;

import java.io.File;
import java.io.FilenameFilter;

public class DirectoryLoader {

    private final String EXTENSION_TXT = ".txt";
    private final String EXTENSION_XML = ".xml";

    public File[] load (String path) {
        //CREDITS: https://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder

        File dir = new File(path);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(EXTENSION_TXT);
            }
        });

    }

    public String[] loadFileNames (String path) {
        //CREDITS: https://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder

        File dir = new File(path);

        File[] arr = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(EXTENSION_XML);
            }
        });

        if (arr != null) {
            String[] names = new String[arr.length];
            for (int i = 0; i < arr.length; i++) {
                names[i] = arr[i].getName();
            }
            return names;
        } else {
            System.out.println("XML PACKS NOT FOUND!");
            System.exit(0);
        }

        return null;
    }

}
