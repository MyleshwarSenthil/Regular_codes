import java.util.*;

class objects_classes{
    static double radius;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the circle radius: ");
        radius = sc.nextDouble();
        circle c = new circle();
        c.area();
        c.circum();
        
    }
}
class circle{
    
    static objects_classes r = new objects_classes();
    public void area(){
        System.out.println("Area: "+3.14 * r.radius * r.radius);
    }
    public void circum(){
        System.out.println("Circumstance: "+2 * 3.14 * r.radius);
    }
}
