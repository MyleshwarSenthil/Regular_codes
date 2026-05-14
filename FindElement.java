import java.util.*;

class FindElement{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the size of  2 array: ");
        int size= sc.nextInt();

        int [] arr1 = new int[size];

        int [] arr2 = new int[size];

        for(int i=0;i<size;i++){
            System.out.println("Enter the 1st array elements: ");
            arr1[i] = sc.nextInt();
        }

        for(int j=0;j<size;j++){
            System.out.println("Enter the 2nd array elements: ");
            arr2[j] = sc.nextInt();
        }
        for(int k=0;k<arr1.length;k++){
            for(int l=0;l<arr2.length;l++){
                if(arr1[k]==arr2[l]){
                    System.out.println(arr1[k]);
                }
            }
        }
    }
}