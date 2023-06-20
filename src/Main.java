import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static GameProgress saving = new GameProgress(100, 30, 2, 600.75);
    static GameProgress savingTwo = new GameProgress(90, 59, 10, 800);
    static GameProgress savingThree = new GameProgress(60, 79, 15, 945);
    static List listZipFiles = new ArrayList<>();
    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            listZipFiles.add(path);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void zipFiles(String zipPath, List listZipFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(zipPath));
             FileInputStream fis = new FileInputStream("/Games/savegames/save1.dat")) {
            for (int i = 0; i < listZipFiles.size(); i++) {
                ZipEntry entry = new ZipEntry((String) listZipFiles.get(i));
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();}
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }
    public static void main(String[] args) {
        saveGame("/Games/savegames/save1.dat", saving);
        saveGame("/Games/savegames/save2.dat", savingTwo);
        saveGame("/Games/savegames/save3.dat", savingThree);
        zipFiles("/Games/savegames/zip.zip", listZipFiles);
        deleteFile("/Games/savegames/save1.dat");
        deleteFile("/Games/savegames/save2.dat");
        deleteFile("/Games/savegames/save3.dat");
    }
}