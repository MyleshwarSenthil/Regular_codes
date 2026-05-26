import java.util.*;


class arrreverse{
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of array: ");
        int size= sc.nextInt();

        int [] arr = new int[size];

        for(int i=0;i<arr.length;i++){
            System.out.println("Enter the value of array: ");
            arr[i] = sc.nextInt();
        }

        for(int j=0;j<arr.length;j++){
            System.out.print(arr[j]);
        }
        
            System.out.println(" ");
        for(int k=arr.length-1;k>=0;k--){
            System.out.print(arr[k]);
        }
    }
}