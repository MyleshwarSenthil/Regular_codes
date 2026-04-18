import java.util.*;

class samplearr{
    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the size: ");
        int size =sc.nextInt();
        int [] arr = new int[size];
        for (int i=0; i<size; i++){
            System.out.print("Enter the value of "+(i+1)+": ");
            arr[i] = sc.nextInt();
        }
        System.out.print("Array values are: ");
        for(int j=0; j<arr.length; j++){
            System.out.print(arr[j]);
        }
    }
}