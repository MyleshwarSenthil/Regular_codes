import java.util.*;

class MyArayLstEx{
    public static void main(String [] args){
        araylst();
    }

    public static void araylst(){
        ArrayList<String> car = new ArrayList<>(); // OR var car = new ArrayList<String>();

        car.add("Honda");
        car.add("Hyundai");
        car.add("Toyota");

        System.out.println("Array has: "+car);

        System.out.println("Value at index 1: "+ car.get(1));

        car.remove(1);

        System.out.println("After removing Hyundai: "+car);

        car.add(2, "Skoda");

        System.out.println("After adding Skoda: "+car);

        car.set(2, "Audi");

        System.out.println("After setting Audi: "+car);

        for( int i = 0; i< car.size();  i++){
            System.out.println(car.get(i));
        }
    }
}