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
import java.util.StringTokenizer;

public class Database {
    private ArrayList users;
    private ArrayList courses;
    private static final String SEPARATOR = ",";

    public Database() throws IOException, URISyntaxException {
        String fullPath = getFileFromURL("db/users");
//        String fullPath = "src/resources/db/users";
        users = readFile(fullPath, "users");
    }
    
    public void printStudentInfo(){
        for (int i = 0; i < users.size(); i++){

            Object user = users.get(i);
            if (user instanceof Student){
                ((Student) user).printStudentInfo();
            }else{
                System.out.println("Not student!");
            }
        }
    }

    private String getFileFromURL(String fileName) throws URISyntaxException {
        URL res = getClass().getClassLoader().getResource(fileName);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    /** Read the contents of the given file. */
    private static List read(String fileName) throws IOException {
        List data = new ArrayList() ;
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        try {
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        }
        finally{
            scanner.close();
        }
        return data;
    }

    private static ArrayList readFile(String fileName, String type) throws IOException{
        ArrayList stringArray = (ArrayList)read(fileName);
        ArrayList itemList = new ArrayList() ;// to store Professors data

        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            String[] tokens = st.split(SEPARATOR);
            Object toAdd;
            switch(type){
                case "users":
                    //fields for user table: username,password,fullname,gender,matric,major,indexes,yearOfStudy,AU,admin_access
                    if(tokens[9].equals("0")){
                        //Student
                        toAdd = new Student(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], Integer.parseInt(tokens[8]));
                    } else {
                        //Admin
                        toAdd = new Admin(tokens[0], tokens[1], tokens[2], tokens[3]);
                    }
                    itemList.add(toAdd);
                    break;
                default:
                    break;
            }

        }
        return itemList ;
    }

    private void saveFile(String fileName){

    }
}
