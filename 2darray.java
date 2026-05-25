import java.util.*;

class main{
    public static void main(String[] ars){
        // int [][] a= {
        //     {1,2,3},
        //     {4,5,6},
        //     {7,8,9}

        // };

        // for(int i=0;i<a.length;i++){
            
        //     for(int j=0;j<a[i].length;j++){
        //         System.out.print(a[i][j]);
        //     }
        // }

        Scanner sc = new Scanner(System.in);

        int row= sc.nextInt();
        int col = sc.nextInt();

        int [][]a = new int[row][col];

        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                a[i][j] = sc.nextInt();
            }
        }

        for(int k=0;k<a.length;k++){
            for(int l=0;l<a[k].length;l++){
                System.out.println(a[k][l]+" ");
            }
        }
    }
}