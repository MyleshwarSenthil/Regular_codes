import java.util.*;

class reverse_string{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String user ="";
        oper o=new oper();
        do { 
            o.dooper();
            System.out.print("Do you want to continue: ");
            user = sc.nextLine();
        } while (user.equals("y"));
     
    }
}

class oper{
    public void dooper(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter a string: ");
        String user = sc.nextLine();
        String output = "";
        for (int i=user.length()-1; i>=0; i--){
            output = output +user.charAt(i);
        }
        System.out.println("Reversed string is: "+output);
    }
}