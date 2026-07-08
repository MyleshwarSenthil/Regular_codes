import java.util.*;

class intReverse{
    public static void main(String[] args){
        Scanner sc= new Scanner(System.in);

        System.out.print("Enter the int: ");
        int a = sc.nextInt();

        String ori = Integer.toString(a);

        System.out.print("Reversed int value is: ");

        for(int i=ori.length()-1;i>=0;i--){
            System.out.print(ori.charAt(i));
        }
    }
}