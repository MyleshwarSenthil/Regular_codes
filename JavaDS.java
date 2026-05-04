import java.util.*;

class  JavaDS{
    public static void main(String[] args){
        arraylist();
        hashset();
        hashmap();
    }

    public static void arraylist(){
        ArrayList<String> car = new ArrayList<>();

        car.add("Volvo");
        car.add("Nissan");
        car.add("Honda");

        System.out.println(car);

      
    }

    public static void hashset(){
       try{
        HashSet <Integer> num = new HashSet<>();

        num.add(1);
        num.add(2);
        num.add(3);
        num.add(3);
        System.out.println(num);
       }
       catch(Exception e){
        System.out.println("Exception caught");
       }
    }

    public static void hashmap(){
        HashMap<String, Integer> books = new HashMap<>();

        books.put("Philosopher", 2);
        books.put("Void", 3);
        books.put("Into nothing", 2);

        System.out.println(books);
    }
}