import java.io.*;

class FileStream{
    public static void main(String [] args){

        try(FileInputStream f = new FileInputStream("summa1.txt")){
            
            int i;

            while((i = f.read()) != -1){
                System.out.print((char) i);

            }
        }
        catch(Exception e){
            System.out.println("Exception caught");
        }
    }
}