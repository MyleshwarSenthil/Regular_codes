class overRideEx{
    public  static void main(String[] args){
        sample s = new sample();
        sample2 a = new sample2();
        s.sum();
        a.sum();
    }
}

class sample{
    public static void sum(){
        System.out.println("Sample");
    }
}

class sample2 extends sample{
    public static void sum(){
        System.out.println("This is the overrided method");
    }    
}