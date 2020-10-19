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
    private ArrayList<Object> users;
//    private ArrayList<Course> courses;
    private static final String SEPARATOR = ",";

    public Database() throws Exception {
        users = readFile(getFilePath("db/users"), "users");
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

    /** Get absolute path. */
    private String getFilePath(String fileName) throws Exception {
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

    /** Converts String to objects/entities */
    private static ArrayList<Object> readFile(String fileName, String type) throws IOException{
        ArrayList<String> stringArray = (ArrayList<String>)read(fileName);
        ArrayList<Object> itemList = new ArrayList<>() ;

        for (String st : stringArray) {
            String[] tokens = st.split(SEPARATOR);
            switch (type) {
                case "users":
                    User toAdd;
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
//                case "course":
//                    Course toAdd;
                default:
                    break;
            }

        }
        return itemList ;
    }

    public void save(String type) throws Exception {
        List<String> writeList = new ArrayList<>();
        String fullPath;
        switch(type){
            case "users":
                for (int i = 0; i < users.size(); i++){
                    User user = (User) users.get(i);
                    writeList.add(user.formatDBRow());
                }
                fullPath = getFilePath("db/users");
                write(fullPath, writeList);
                break;
            case "course":
                break;
            default:
                break;
        }

    }


    /** Query specific user by username */
    public User getUser(String username){
        User user = null;

        for (int  i = 0; i < this.users.size(); i++){
            User temp = (User) users.get(i);
            if (temp.getUserName().equals(username)){
                user = temp;
                break;
            }
        }
        return user;
    }

    /** return all users */
    public User[] getUsers(){
        User[] users = new User[this.users.size()];

        for (int i = 0; i < this.users.size(); i++){
            users[i] = (User) this.users.get(i);
        }

        return users;
    }

    public boolean addUser(User user){
        for (int  i = 0; i < this.users.size(); i++){
            User temp = (User) users.get(i);
            if (temp.getUserName().equals(user.getUserName())){
                return false;
            }
        }
        users.add(user);
        return true;
    }
}
