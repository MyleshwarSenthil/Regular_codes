import java.util.*;

class primenum{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String user;

        do{
        System.out.println("Enter the int value: ");
        int a = sc.nextInt();

        if(check(a)==true){
            System.out.println(a + " is a prime number");
        }
        else{
            System.out.println(a+ " is not a prime number");
        }

        System.out.println("Do you want to continue again? ");
        String waste = sc.nextLine();
        user = sc.nextLine();
        }while(user.equals("y"));
        System.out.println("Ok thank you!");
       

    }

    public static boolean check(int b){
        if(b==2 || b==3 || b==5 || b==7){
            return(true);
        }

        if(b%2==0 || b%3==0 || b%5==0 || b%7==0){
            return(false);
        }

        return(true);
    }
}
