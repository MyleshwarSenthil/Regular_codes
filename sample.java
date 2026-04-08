import java.util.*;

class sample{
    public static void main(String[] args) {
        Scanner  sc = new Scanner(System.in);
        System.out.println("----Array Program!----");
        System.out.print("Enter the array size: ");
        int size =  sc.nextInt();
        int [] arr=new int[size];
        for(int i=0;i<size;i++){
            System.out.print("Enter the array value "+(i+1)+":");
            arr[i] = sc.nextInt();
        }
        System.out.print("Array elements are: ");
        for(int j=0;j<arr.length;j++){
            System.out.println(arr[j]);
        }
        System.out.println("Hai");
    }
}