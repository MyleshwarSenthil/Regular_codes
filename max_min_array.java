import java.util.*;

class max_min_array{
    public static void main(String[] args){
        dooper d=new dooper();
        String user="";
        do { 
            d.oper();
            Scanner sc=new Scanner(System.in);
            System.out.print("Do you want to continue: ");
            user = sc.nextLine();
        } while (user.equals("y"));
    }
}

class dooper{
    public void oper(){
        
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the size of array: ");
        int size = sc.nextInt();
        int [] arr =new int[size];
        for (int k=0; k<size; k++){
            System.out.print("Enter array value "+ (k+1)+": ");
            arr [k]=sc.nextInt();

        }
        int temp=0;
        long tempmin=999999999;
        for(int i=0; i<=arr.length-1; i++){
            if(arr[i]>temp){
                temp = arr[i];
            }
        }
        for(int j=0; j<=arr.length-1;j++){
            if(arr[j]<tempmin){
                tempmin = arr[j];
            }
        }
        System.out.println("Smallest element in array is: "+tempmin);
        System.out.println("Largest element in array is: "+temp);
    }
}