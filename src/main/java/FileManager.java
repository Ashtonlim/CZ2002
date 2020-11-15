import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
// Note : When structure of the Object type (the class file) in the list changed
// the Serialized file may fail.
public class FileManager
{
    private static String DBPath;

    public FileManager() throws Exception {
        DBPath = getFilePath("db/database");
    }

    public static List readSerializedObject(String filename) throws IOException, ClassNotFoundException {
        List pDetails = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        fis = new FileInputStream(filename);
        in = new ObjectInputStream(fis);
        pDetails = (ArrayList) in.readObject();
        in.close();
//        try {
//            fis = new FileInputStream(filename);
//            in = new ObjectInputStream(fis);
//            pDetails = (ArrayList) in.readObject();
//            in.close();
//        } catch (InvalidClassException ex){
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }

        return pDetails;
    }

    public static void writeSerializedObject(String filename, List list) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(list);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Get absolute path. */
    public String getFilePath(String fileName) throws Exception {
        URL res = getClass().getClassLoader().getResource(fileName);

        if (res != null) {
            File file = Paths.get(res.toURI()).toFile();
            return file.getAbsolutePath();
        } else{
            throw new Exception("DB file not found on the system");
        }

    }

    public ArrayList<User> loadUsers(List temp){
        ArrayList<User> list = new ArrayList<>();
        try{

            for (Object o : temp){
                list.add((User) o);
            }

        } catch(Exception e){
            System.out.println("DB data mismatch, will return empty array.");
            System.out.println(e);
            list = new ArrayList<>();
        }

        return list;
    }

    public ArrayList<Course> loadCourses(List temp){
        ArrayList<Course> list = new ArrayList<>();
        try{

            for (Object o : temp){
                list.add((Course) o);
            }

        } catch(Exception e){
            System.out.println("DB data mismatch, will return empty array.");
            System.out.println(e);
            list = new ArrayList<>();
        }

        return list;
    }

    public ArrayList<Object> loadAll(){
        ArrayList<Object> store = new ArrayList<>();
        try{
            List temp = readSerializedObject(DBPath);
            store.add( loadUsers( (List) temp.get(0) ) );
            store.add( loadCourses( (List) temp.get(1) ) );
        } catch(Exception e){
            System.out.println("Critical: Error trying to load DB, returning empty database. You should run loadDummyData() once.");
            store.add( new ArrayList<>());
            store.add( new ArrayList<>());
        }

        return store;
    }

    public void saveAll(ArrayList<Object> db){
        writeSerializedObject(DBPath, db);
    }
}