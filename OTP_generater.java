import java.util.*;

class OTP_generator{
    public static void main(String[] args){
        String s;
        Scanner sc=new Scanner(System.in);
        do { 
            Scanner z=new Scanner(System.in);
            Random r=new Random();
            int b=r.nextInt((9999 - 1000) + 1)+ 1000;
            System.out.println(b);
            System.out.print("Enter the OTP: ");
            int y=z.nextInt();
            if(y==b){
                System.out.println("OTP verified...");
            }
            else{
                System.out.println("Enter valid OTP");
            }
            System.out.print("Do you want to continue? ");
            s=sc.nextLine();
            
        } while (s.equals("y"));
    }
}