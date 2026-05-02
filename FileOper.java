import java.io.*;
import java.util.*;

class FileOper{
    public static void main(String[] aegs){
        File f = new File("faaaaa.txt");
        try{
        Scanner sc= new Scanner(f);
        String d = sc.nextLine();
        System.out.println(d);

        }
        catch(IOException e){
            System.out.println("dfgh");
        }
    }
}