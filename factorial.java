import java.util.*;

class factorial{
    public static void main(String[] args){
        oper o = new oper();
        String user;
        do { 
            o.dooper();
            Scanner scan=new Scanner(System.in);
            System.out.print("Do you want to continue: ");
            user = scan.nextLine();
        } while (user.equals("y"));
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
        System.out.println(a);
    }
}