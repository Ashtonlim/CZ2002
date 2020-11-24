package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The Class for handling the connecting to, writing to and reading from the
 * database.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */

// the Serialized file may fail.
public class FileManager {

    /** The DB path. */
    private static String DBPath;
    static {

        try {
            DBPath = getFilePath("db/database");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Read serialized object.
     *
     * @return the array list
     * @throws Exception the exception
     */
    public static ArrayList<?> readSerializedObject() throws Exception {
        ArrayList<?> read = new ArrayList<Object>();
        System.out.println(DBPath);
        // try {
        FileInputStream fis = new FileInputStream(DBPath);
        ObjectInputStream in = new ObjectInputStream(fis);
        read = (ArrayList<?>) in.readObject();
        in.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        return read;

    }

    /**
     * Write serialized object.
     *
     * @param list the list
     */
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

    /**
     * Get absolute path.
     *
     * @param filename the filename
     * @return the file path
     * @throws Exception the exception
     */
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

    /**
     * Load users.
     *
     * @param temp the temp
     * @return the array list
     */
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

    /**
     * Load faculties.
     *
     * @param temp the temp
     * @return the array list
     */
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