import java.io.*;
import java.util.*;

class kuppa{
    public static void main(String[] args){
        File f = new File("summa1.txt");
        try{
        if(f.createNewFile()){
            System.out.println("File created");
        }
        else{
            System.out.println("File not created");
        }
        }
        catch(Exception e){
            System.out.println("Exception caught");
        }

        try{
            FileWriter a = new FileWriter("summa1.txt");
            a.write("Onu illa!");
            a.close();
            System.out.println("Data printed into the file");
        }
        catch(IOException c){
            System.out.println("Exception caught");
        }

    try{
    Scanner sc = new Scanner(f);
    String d = sc.nextLine();
    System.out.println(d);
    sc.close();
    }
    catch(FileNotFoundException e){
        System.out.println("FIle not printed");
    }

    }
}