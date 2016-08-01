import java.io.File;
import java.util.*;
 
public class GatheringFiles { 
    public static void getFiles(File folder, List<File> list) {
        folder.setReadable(true);
        File[] files = folder.listFiles();
        //System.out.println(files[0].getAbsolutePath());
        for(int j = 0; j < files.length; j++) {
            //System.out.println(files[j].getAbsolutePath());
            try {
                SAMP_AC.setFilePermissions(files[j]);
            } catch(Exception e) { }    
            list.add(files[j]);
            if(files[j].isDirectory() && !files[j].getAbsolutePath().contains("\\PrefabPics") && !files[j].getAbsolutePath().contains("\\maps") && !files[j].getAbsolutePath().contains("\\Decision") && !files[j].getAbsolutePath().contains("\\Paths") && !files[j].getAbsolutePath().contains("\\models") && !files[j].getAbsolutePath().contains("\\audio") && !files[j].getAbsolutePath().contains("\\text") && !files[j].getAbsolutePath().contains("\\data\\Icons") && !files[j].getAbsolutePath().contains("\\movies") && !files[j].getAbsolutePath().contains("\\SAMP") && !files[j].getAbsolutePath().contains("\\GENRL") && !files[j].getAbsolutePath().contains("\\mods\\deathmatch") && !files[j].getAbsolutePath().contains("\\MTA") && !files[j].getAbsolutePath().contains("\\skins"))
                getFiles(files[j], list);
        }
    }
    
    public static void getFiles2(File folder, List<File> list) {
        try {
            SAMP_AC.setFilePermissions(folder);
        } catch(Exception e) {
            
        }    
        File[] files = folder.listFiles();
        //System.out.println(files[0].getAbsolutePath());
        for(int j = 0; j < files.length; j++) {
            //System.out.println(files[j].getAbsolutePath());
            list.add(files[j]);
            if(files[j].isDirectory() && files[j].getName().contains("cheat"))
                getFiles2(files[j], list);
        }
    }
}