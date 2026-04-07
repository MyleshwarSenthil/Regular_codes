import java.util.*;

class bank{
    public static void main(String[] args){
        
        Scanner sc=new Scanner(System.in);
        System.out.print("Do you want to continue: ");
        int a=sc.nextInt();
        samp s=new samp();
        do { 
            s.ope();
        
        } while (a=='y');
    }
}

class samp{
    public void ope(){
        Scanner sc=new Scanner(System.in);
        System.out.println("What do you want to do? ");
        System.err.println("1:Deposit\n2:Withdrawl \n3:Check balance");
        System.err.println("Enter your option: ");
        int userinput=sc.nextInt();
        oper o = new oper();
        if(userinput==1){
            o.deposit();
        }
        else if(userinput==2){
            o.Withdrawl();
        }
        else if(userinput==3){
            o.balance();
        }
        else{
            System.out.println("Enter a valid option...");
        }
    }
}

class oper{
    double balance=1000.00;
    Scanner sc=new Scanner(System.in);

    public void deposit(){
        System.out.print("Enter how much want to deposit: ");
        int user = sc.nextInt();
        balance += user;
        System.out.println("Available balance is: "+balance);
    }

    public void Withdrawl(){
        System.out.print("Enter how much want to withdrawl: ");
        int a= sc.nextInt();
        if(a<=balance){
            balance-=a;
            System.out.println("Available  balance is: "+ balance);
        }
        else{
            System.out.println("Enter valid amount!");
        }
    }

    public void balance(){
        System.out.println("Available balance is: "+balance);
    }
}

