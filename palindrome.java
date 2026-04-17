import java.util.*;

class palindrome{
    public static void main(String [] args){
        check c=new check();
        String user="";
        do { 
            Scanner sc=new Scanner(System.in);
            c.dooper();
            System.out.print("Do you want to continue: ");
            user = sc.nextLine();

        } while (user.equals("y"));
        System.out.println("Thnks bye...");
    }
}

class check{
    public void dooper(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter a string: ");
        String a=sc.nextLine();
        String b="";
        for (int i=a.length()-1; i>=0; i--){
            b=b+a.charAt(i);
        }
        if(a.equals(b)){
            System.out.println(a+" is a palindrome");
        }
        else{
            System.out.println(a+" is not a palindrome");
        }
    }
}