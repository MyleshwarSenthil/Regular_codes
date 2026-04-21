import java.util.*;

class comp_interest{
    public static void main(String[] args){
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
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the principle amount: ");
        double amount = sc.nextDouble();
        System.out.print("Enter rate of interest: ");
        double rate = sc.nextDouble();
        System.out.print("Enter the time in year: ");
        double year = sc.nextDouble();

        double interest;
        double result = 0;
        for(int i=1; i<= year;i++){
            interest = amount / 100 * rate;
            amount += interest;
            result += interest;
        }
        System.out.println("Total amount with compound interest is: "+ amount);
        System.out.println("Total compound interest is: "+amount);
    } 
}