import java.util.*;

public class cal{
    public static void main(String[] args){
        int a;
        do{
            Scanner s=new Scanner(System.in);
            System.out.print("Do you want to continue: ");
            a=s.nextInt();
            oper o =new oper();
            o.dooper();
        }while(a==1);
    }
}

class oper{
    public void dooper(){
        Scanner sc=new Scanner(System.in);
        add a = new add();
        sub b=new sub();
        mul m=new mul();
        div d=new div();
        
        System.out.print("What do you want to do? ");
        System.out.println("\n1:Add\n2:Sub \n3:Mul \n4:Div");
        int z=sc.nextInt();
        if(z==1){
            a.addoper();
        }
        else if(z==2){
            b.suboper();
        }
        else if(z==3){
            m.muloper();
        }
        else if(z==4){
            d.divoper();
        }
        
    }
}

class add{
    public void addoper(){
        Scanner sc=new Scanner(System.in);
        
        System.out.print("Enter the first number: ");
        int a=sc.nextInt();
        
        System.out.print("Enter the second number: ");
        int b=sc.nextInt();
        System.out.println("Addition of two numbers is: "+ (a+b));
    }
}

class sub{
    public void suboper(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the first number: ");
        int a=sc.nextInt();
        
        System.out.print("Enter the second number: ");
        int b=sc.nextInt();
        System.out.println("Subraction of two numbers is: "+ (a-b));
    }
}

class mul{
    public void muloper(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the first number: ");
        int a=sc.nextInt();
        
        System.out.print("Enter the second number: ");
        int b=sc.nextInt();
        System.out.println("Multiplication of two numbers is: "+ (a*b));
    }
}


class div{
    public void divoper(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the first number: ");
        int a=sc.nextInt();
        
        System.out.print("Enter the second number: ");
        int b=sc.nextInt();
        System.out.println("Division of two numbers is: "+ (a/b));
    }
}