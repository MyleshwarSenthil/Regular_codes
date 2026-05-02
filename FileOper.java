import  java.io.*;

class FileOper{
    public static void main(String[] args){
        try{
        FileWriter f = new FileWriter("waste.txt");
        f.write("This contains nothing!");
        f.close();
        System.out.println("Data Writed");
        }
        catch(Exception e){
            System.out.println("Exception caught");
        }
    }
}