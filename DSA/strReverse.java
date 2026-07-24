import java.util.*;

class strReverse{
    public static void main(String [] args){
        solution s = new solution();
        System.out.println("(abcd) :"+ s.check("(abcd)"));          
        System.out.println("(u(like)i) :"+s.check("(u(like)i)"));       
        System.out.println("(ed(et(oc))el) :"+s.check("(ed(et(oc))el)"));   
    }
}

class solution{
    String check(String s){
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder current = new StringBuilder();
        
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            
            if(c == '('){
                stack.push(current);
                current = new StringBuilder();
            } else if(c == ')'){
                current.reverse();
                current = stack.pop().append(current);
            } else {
                current.append(c);
            }
        }
        
        return current.toString();
    }
}

