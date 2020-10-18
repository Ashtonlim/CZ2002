import java.util.*;
import java.io.*;

public class MyStarsApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Student[] S = new Student[5];

        for (int i = 0; i < 5; i++) {
            S[i] = new Student( Integer.toString(i), "SCSE", "2", 10);
        }

//        for (int i = 0; i < 5; i++) {
//            S[i].printStudentInfo();
//        }

        Index x = new Index("1", 100, S, S);
        x.printWaitList();


    }
}
