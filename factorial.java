import java.util.*;

public class factorial{
    public static void main(String[] args){
        System.out.println("---Welcome to Factorial operation---");
        oper o = new oper();
        String user;
        do { 
            o.dooper();
            Scanner scan=new Scanner(System.in);
            System.out.print("Do you want to continue: \nIf yes : \"y or Y\"\nIf no: \"n or N\" ");
            user = scan.nextLine();
        } while (user.equals("y")||user.equals("Y"));
        System.out.println("Thanks Bye...");
    }
}

class oper{
    public void dooper(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter a number: ");
        int a = sc.nextInt();
        for(int i=a-1;i>0;i--){
            a = a*i;
        }
        System.out.println("Factorial is: "+a);
    }
}