import java.util.*;

class fiba{
    public static void main(String[] args) {
        oper o=new oper();
        String user;
        do { 
            Scanner sc=new Scanner(System.in);
            o.dooper();
            System.out.print("Do you want to continue: ");
            user = sc.nextLine();
        } while (user.equals("y"));
    }
}

class oper{
    public  void dooper(){
        Scanner sc=new Scanner(System.in);
        int a=0;
        int b=1;
        int c;
        System.out.print("Enter the range: ");
        int user = sc.nextInt();
        System.out.print(a+ ", ");
        System.out.print(b+", ");
        for (int i=0;i<=user;i++){
            c=a+b;
            System.out.print(c+", ");
            a=b;
            b=c;
        } 
        
            System.out.println(" ");
    }
}