enum sample{
    LOW, 
    MEDIUM, HIGH
}

class practice{
    public static void main(String [] arfgs){
        sample s = sample.HIGH;
        System.out.println(s);

        for(sample a : sample.values()){
            System.out.println(a);
        }
    }
}