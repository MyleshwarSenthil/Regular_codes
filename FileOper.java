import java.io.*;

class FileOper{
    public static void main(String [] args){
        File a = new File("waste.txt");
        try{
            if(a.createNewFile()){
            System.out.println("File created");

            }
            else{
                System.out.println("File already exists");
            }
        }
        catch(Exception e){
            System.out.println("Cannot create file");
        }
    }
}