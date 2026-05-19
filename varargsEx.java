class varargsEx{
    public static void main(String[] args){
        sum(1,2,3,4,5);
        sum(2,3,4,5,6);
        sum(9,8,7,6,5);
    }

    static void sum(int ...varargs){
        int a = 0;

        for(int j: varargs){
            System.out.print(j);
        }

        for(int i:varargs){
            a+=i;
        }
        System.out.println(" ");
        System.out.println("Sum: "+a);
    }
}