import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.net.*;
import java.io.File;
import java.nio.file.Paths;

public class Database {

    private static final String SEPARATOR = ",";

    public Database() throws Exception {

        /** Check if db files are accessible */
        try{
            getFilePath("db/users");
//            getFilePath("db/courses");
//            getFilePath("db/indexes");
        }catch (Exception e){
           System.out.println("Unable to load text-based database file: " + e);
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

    /** Read the contents of the given file. */
    private static List<String> read(String fileName) throws IOException {
        List<String> data = new ArrayList<>() ;
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        }
        return data;
    }

    /** Write fixed content to the given file. */
    private static void write(String fileName, List data) throws IOException  {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));

        try {
            for (int i =0; i < data.size() ; i++) {
                out.println((String)data.get(i));
            }
        }
        finally {
            out.close();
        }
    }

    /** Convert String to objects/entities */
    public ArrayList<Object> load(String fileName, String type) throws Exception {
        String filePath = getFilePath(fileName);
        ArrayList<String> stringArray = (ArrayList<String>)read(filePath);
        ArrayList<Object> itemList = new ArrayList<>() ;

        Object toAdd;
        for (String st : stringArray) {
            String[] tokens = st.split(SEPARATOR);
            switch (type) {
                case "user":
                    //fields for user table: username,password,fullname,gender,matric,major,indexes,yearOfStudy,AU,admin_access
                    if (tokens[9].equals("0")) {
                        //Student
                        toAdd = new Student(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]));
                    } else {
                        //Admin
                        toAdd = new Admin(tokens[0], tokens[1], tokens[2], tokens[3]);
                    }
                    itemList.add(toAdd);
                    break;
                case "course":
                    toAdd = new Course(tokens[0], tokens[1], tokens[2], tokens[4], Integer.parseInt(tokens[5]));
                    itemList.add(toAdd);
                    break;
                default:
                    break;
            }

        }
        return itemList ;
    }

    /** Convert objects/entities back to text file*/
    public boolean save(List writeList, String fileName) throws Exception {
        try{
            String fullPath = getFilePath(fileName);
            write(fullPath, writeList);
            return true;
        } catch (Exception e){
            System.out.println("Critical error while saving to db file: " + e + " Your file has not been saved.");
            return false;
        }

    }

}
