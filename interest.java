import java.util.*;

class interest{
    public static void main(String [] args){
        Scanner sc =new Scanner(System.in);
        String user;
        dooper o =new dooper();
        do { 
            o.oper();
            System.out.print("Do you want to continue: ");
            user =sc.nextLine();
        } while (user.equals("y"));
    }
}

class dooper{
    public void oper(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the tenture amount: ");
        double amount = sc.nextInt();
        System.out.print("Enter the year: ");
        int year = sc.nextInt();
        System.out.print("Enter the percentage: ");
        double per = sc.nextInt();
        double result = amount / 100 * per;
        double finalinterest = (result * year);
        System.out.println("Total interest is: "+finalinterest);
    }
}