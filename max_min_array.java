
class max_min_array{
    public static void main(String[] args){
        int [] arr= {1,2,3,4,5,4,8,3};
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