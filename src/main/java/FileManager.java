import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

// Note : When structure of the Object type (the class file) in the list changed
// the Serialized file may fail.
public class FileManager {
    private static String DBPath;
    static {

        try {
            DBPath = getFilePath("db/database");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<?> readSerializedObject() {
        ArrayList<?> read = new ArrayList<Object>();
        System.out.println(DBPath);
        try {
            FileInputStream fis = new FileInputStream(DBPath);
            ObjectInputStream in = new ObjectInputStream(fis);
            read = (ArrayList<?>) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return read;

    }

    public static void writeSerializedObject(ArrayList<Object> list) {

        try {
            FileOutputStream fos = new FileOutputStream(DBPath);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(list);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Get absolute path. */
    public static String getFilePath(String filename) throws Exception {

        URL res = new Object() {
        }.getClass().getClassLoader().getResource(filename);

        if (res != null) {
            File file = Paths.get(res.toURI()).toFile();
            return file.getAbsolutePath();
        } else {
            throw new Exception("DB file not found on the system");
        }

    }

    public static ArrayList<User> loadUsers(ArrayList<Object> temp) {
        ArrayList<User> list = new ArrayList<User>();
        try {

            for (Object o : temp) {
                list.add((User) o);
            }

        } catch (Exception e) {
            System.out.println("DB data mismatch, will return empty array.");
            System.out.println(e);
        }

        return list;
    }

    public ArrayList<Faculty> loadFaculties(ArrayList<Faculty> temp) {
        ArrayList<Faculty> list = new ArrayList<Faculty>();
        try {

            for (Object o : temp) {
                list.add((Faculty) o);
            }

        } catch (Exception e) {
            System.out.println("DB data mismatch, will return empty array.");
            System.out.println(e);
            list = new ArrayList<>();
        }

        return list;
    }

}